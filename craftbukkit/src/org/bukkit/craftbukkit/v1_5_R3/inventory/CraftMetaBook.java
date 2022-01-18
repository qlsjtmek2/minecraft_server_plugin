// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.inventory;

import org.bukkit.inventory.meta.Repairable;
import org.bukkit.inventory.meta.ItemMeta;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang.Validate;
import com.google.common.base.Strings;
import org.bukkit.Material;
import net.minecraft.server.v1_5_R3.NBTBase;
import java.util.Map;
import net.minecraft.server.v1_5_R3.NBTTagList;
import net.minecraft.server.v1_5_R3.NBTTagString;
import net.minecraft.server.v1_5_R3.NBTTagCompound;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.inventory.meta.BookMeta;

@DelegateDeserialization(SerializableMeta.class)
class CraftMetaBook extends CraftMetaItem implements BookMeta
{
    static final ItemMetaKey BOOK_TITLE;
    static final ItemMetaKey BOOK_AUTHOR;
    static final ItemMetaKey BOOK_PAGES;
    static final int MAX_PAGE_LENGTH = 256;
    static final int MAX_TITLE_LENGTH = 65535;
    private String title;
    private String author;
    private List<String> pages;
    
    CraftMetaBook(final CraftMetaItem meta) {
        super(meta);
        this.pages = new ArrayList<String>();
        if (!(meta instanceof CraftMetaBook)) {
            return;
        }
        final CraftMetaBook bookMeta = (CraftMetaBook)meta;
        this.title = bookMeta.title;
        this.author = bookMeta.author;
        this.pages.addAll(bookMeta.pages);
    }
    
    CraftMetaBook(final NBTTagCompound tag) {
        super(tag);
        this.pages = new ArrayList<String>();
        if (tag.hasKey(CraftMetaBook.BOOK_TITLE.NBT)) {
            this.title = tag.getString(CraftMetaBook.BOOK_TITLE.NBT);
        }
        if (tag.hasKey(CraftMetaBook.BOOK_AUTHOR.NBT)) {
            this.author = tag.getString(CraftMetaBook.BOOK_AUTHOR.NBT);
        }
        if (tag.hasKey(CraftMetaBook.BOOK_PAGES.NBT)) {
            final NBTTagList pages = tag.getList(CraftMetaBook.BOOK_PAGES.NBT);
            final String[] pageArray = new String[pages.size()];
            for (int i = 0; i < pages.size(); ++i) {
                final String page = ((NBTTagString)pages.get(i)).data;
                pageArray[i] = page;
            }
            this.addPage(pageArray);
        }
    }
    
    CraftMetaBook(final Map<String, Object> map) {
        super(map);
        this.pages = new ArrayList<String>();
        this.setAuthor(SerializableMeta.getString(map, CraftMetaBook.BOOK_AUTHOR.BUKKIT, true));
        this.setTitle(SerializableMeta.getString(map, CraftMetaBook.BOOK_TITLE.BUKKIT, true));
        final Iterable<?> pages = SerializableMeta.getObject((Class<Iterable<?>>)Iterable.class, map, CraftMetaBook.BOOK_PAGES.BUKKIT, true);
        CraftMetaItem.safelyAdd(pages, this.pages, 256);
    }
    
    void applyToItem(final NBTTagCompound itemData) {
        super.applyToItem(itemData);
        if (this.hasTitle()) {
            itemData.setString(CraftMetaBook.BOOK_TITLE.NBT, this.title);
        }
        if (this.hasAuthor()) {
            itemData.setString(CraftMetaBook.BOOK_AUTHOR.NBT, this.author);
        }
        if (this.hasPages()) {
            itemData.set(CraftMetaBook.BOOK_PAGES.NBT, CraftMetaItem.createStringList(this.pages, CraftMetaBook.BOOK_PAGES));
        }
    }
    
    boolean isEmpty() {
        return super.isEmpty() && this.isBookEmpty();
    }
    
    boolean isBookEmpty() {
        return !this.hasPages() && !this.hasAuthor() && !this.hasTitle();
    }
    
    boolean applicableTo(final Material type) {
        switch (type) {
            case WRITTEN_BOOK:
            case BOOK_AND_QUILL: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public boolean hasAuthor() {
        return !Strings.isNullOrEmpty(this.author);
    }
    
    public boolean hasTitle() {
        return !Strings.isNullOrEmpty(this.title);
    }
    
    public boolean hasPages() {
        return !this.pages.isEmpty();
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public boolean setTitle(final String title) {
        if (title == null) {
            this.title = null;
            return true;
        }
        if (title.length() > 65535) {
            return false;
        }
        this.title = title;
        return true;
    }
    
    public String getAuthor() {
        return this.author;
    }
    
    public void setAuthor(final String author) {
        this.author = author;
    }
    
    public String getPage(final int page) {
        Validate.isTrue(this.isValidPage(page), "Invalid page number");
        return this.pages.get(page - 1);
    }
    
    public void setPage(final int page, final String text) {
        if (!this.isValidPage(page)) {
            throw new IllegalArgumentException("Invalid page number " + page + "/" + this.pages.size());
        }
        this.pages.set(page - 1, (text == null) ? "" : ((text.length() > 256) ? text.substring(0, 256) : text));
    }
    
    public void setPages(final String... pages) {
        this.pages.clear();
        this.addPage(pages);
    }
    
    public void addPage(final String... pages) {
        for (String page : pages) {
            if (page == null) {
                page = "";
            }
            else if (page.length() > 256) {
                page = page.substring(0, 256);
            }
            this.pages.add(page);
        }
    }
    
    public int getPageCount() {
        return this.pages.size();
    }
    
    public List<String> getPages() {
        return (List<String>)ImmutableList.copyOf((Collection<?>)this.pages);
    }
    
    public void setPages(final List<String> pages) {
        this.pages.clear();
        CraftMetaItem.safelyAdd(pages, this.pages, 256);
    }
    
    private boolean isValidPage(final int page) {
        return page > 0 && page <= this.pages.size();
    }
    
    public CraftMetaBook clone() {
        final CraftMetaBook meta = (CraftMetaBook)super.clone();
        meta.pages = new ArrayList<String>(this.pages);
        return meta;
    }
    
    int applyHash() {
        int hash;
        final int original = hash = super.applyHash();
        if (this.hasTitle()) {
            hash = 61 * hash + this.title.hashCode();
        }
        if (this.hasAuthor()) {
            hash = 61 * hash + 13 * this.author.hashCode();
        }
        if (this.hasPages()) {
            hash = 61 * hash + 17 * this.pages.hashCode();
        }
        return (original != hash) ? (CraftMetaBook.class.hashCode() ^ hash) : hash;
    }
    
    boolean equalsCommon(final CraftMetaItem meta) {
        if (!super.equalsCommon(meta)) {
            return false;
        }
        if (meta instanceof CraftMetaBook) {
            final CraftMetaBook that = (CraftMetaBook)meta;
            if (this.hasTitle()) {
                if (!that.hasTitle() || !this.title.equals(that.title)) {
                    return false;
                }
            }
            else if (that.hasTitle()) {
                return false;
            }
            if (this.hasAuthor()) {
                if (!that.hasAuthor() || !this.author.equals(that.author)) {
                    return false;
                }
            }
            else if (that.hasAuthor()) {
                return false;
            }
            if (!(this.hasPages() ? (!that.hasPages() || !this.pages.equals(that.pages)) : that.hasPages())) {
                return true;
            }
            return false;
        }
        return true;
    }
    
    boolean notUncommon(final CraftMetaItem meta) {
        return super.notUncommon(meta) && (meta instanceof CraftMetaBook || this.isBookEmpty());
    }
    
    ImmutableMap.Builder<String, Object> serialize(final ImmutableMap.Builder<String, Object> builder) {
        super.serialize(builder);
        if (this.hasTitle()) {
            builder.put(CraftMetaBook.BOOK_TITLE.BUKKIT, this.title);
        }
        if (this.hasAuthor()) {
            builder.put(CraftMetaBook.BOOK_AUTHOR.BUKKIT, this.author);
        }
        if (this.hasPages()) {
            builder.put(CraftMetaBook.BOOK_PAGES.BUKKIT, this.pages);
        }
        return builder;
    }
    
    static {
        BOOK_TITLE = new ItemMetaKey("title");
        BOOK_AUTHOR = new ItemMetaKey("author");
        BOOK_PAGES = new ItemMetaKey("pages");
    }
}
