// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.org.ibex.nestedvm;

public interface UsermodeConstants
{
    public static final int SYS_null = 0;
    public static final int SYS_exit = 1;
    public static final int SYS_pause = 2;
    public static final int SYS_open = 3;
    public static final int SYS_close = 4;
    public static final int SYS_read = 5;
    public static final int SYS_write = 6;
    public static final int SYS_sbrk = 7;
    public static final int SYS_fstat = 8;
    public static final int SYS_lseek = 10;
    public static final int SYS_kill = 11;
    public static final int SYS_getpid = 12;
    public static final int SYS_calljava = 13;
    public static final int SYS_stat = 14;
    public static final int SYS_gettimeofday = 15;
    public static final int SYS_sleep = 16;
    public static final int SYS_times = 17;
    public static final int SYS_mkdir = 18;
    public static final int SYS_getpagesize = 19;
    public static final int SYS_unlink = 20;
    public static final int SYS_utime = 21;
    public static final int SYS_chdir = 22;
    public static final int SYS_pipe = 23;
    public static final int SYS_dup2 = 24;
    public static final int SYS_fork = 25;
    public static final int SYS_waitpid = 26;
    public static final int SYS_getcwd = 27;
    public static final int SYS_exec = 28;
    public static final int SYS_fcntl = 29;
    public static final int SYS_rmdir = 30;
    public static final int SYS_sysconf = 31;
    public static final int SYS_readlink = 32;
    public static final int SYS_lstat = 33;
    public static final int SYS_symlink = 34;
    public static final int SYS_link = 35;
    public static final int SYS_getdents = 36;
    public static final int SYS_memcpy = 37;
    public static final int SYS_memset = 38;
    public static final int SYS_dup = 39;
    public static final int SYS_vfork = 40;
    public static final int SYS_chroot = 41;
    public static final int SYS_mknod = 42;
    public static final int SYS_lchown = 43;
    public static final int SYS_ftruncate = 44;
    public static final int SYS_usleep = 45;
    public static final int SYS_getppid = 46;
    public static final int SYS_mkfifo = 47;
    public static final int SYS_klogctl = 51;
    public static final int SYS_realpath = 52;
    public static final int SYS_sysctl = 53;
    public static final int SYS_setpriority = 54;
    public static final int SYS_getpriority = 55;
    public static final int SYS_socket = 56;
    public static final int SYS_connect = 57;
    public static final int SYS_resolve_hostname = 58;
    public static final int SYS_accept = 59;
    public static final int SYS_setsockopt = 60;
    public static final int SYS_getsockopt = 61;
    public static final int SYS_listen = 62;
    public static final int SYS_bind = 63;
    public static final int SYS_shutdown = 64;
    public static final int SYS_sendto = 65;
    public static final int SYS_recvfrom = 66;
    public static final int SYS_select = 67;
    public static final int SYS_getuid = 68;
    public static final int SYS_getgid = 69;
    public static final int SYS_geteuid = 70;
    public static final int SYS_getegid = 71;
    public static final int SYS_getgroups = 72;
    public static final int SYS_umask = 73;
    public static final int SYS_chmod = 74;
    public static final int SYS_fchmod = 75;
    public static final int SYS_chown = 76;
    public static final int SYS_fchown = 77;
    public static final int SYS_access = 78;
    public static final int SYS_alarm = 79;
    public static final int SYS_setuid = 80;
    public static final int SYS_setgid = 81;
    public static final int SYS_send = 82;
    public static final int SYS_recv = 83;
    public static final int SYS_getsockname = 84;
    public static final int SYS_getpeername = 85;
    public static final int SYS_seteuid = 86;
    public static final int SYS_setegid = 87;
    public static final int SYS_setgroups = 88;
    public static final int SYS_resolve_ip = 89;
    public static final int SYS_setsid = 90;
    public static final int SYS_fsync = 91;
    public static final int AF_UNIX = 1;
    public static final int AF_INET = 2;
    public static final int SOCK_STREAM = 1;
    public static final int SOCK_DGRAM = 2;
    public static final int HOST_NOT_FOUND = 1;
    public static final int TRY_AGAIN = 2;
    public static final int NO_RECOVERY = 3;
    public static final int NO_DATA = 4;
    public static final int SOL_SOCKET = 65535;
    public static final int SO_REUSEADDR = 4;
    public static final int SO_KEEPALIVE = 8;
    public static final int SO_BROADCAST = 32;
    public static final int SO_TYPE = 4104;
    public static final int SHUT_RD = 0;
    public static final int SHUT_WR = 1;
    public static final int SHUT_RDWR = 2;
    public static final int INADDR_ANY = 0;
    public static final int INADDR_LOOPBACK = 2130706433;
    public static final int INADDR_BROADCAST = -1;
    public static final int EPERM = 1;
    public static final int ENOENT = 2;
    public static final int ESRCH = 3;
    public static final int EINTR = 4;
    public static final int EIO = 5;
    public static final int ENXIO = 6;
    public static final int E2BIG = 7;
    public static final int ENOEXEC = 8;
    public static final int EBADF = 9;
    public static final int ECHILD = 10;
    public static final int EAGAIN = 11;
    public static final int ENOMEM = 12;
    public static final int EACCES = 13;
    public static final int EFAULT = 14;
    public static final int ENOTBLK = 15;
    public static final int EBUSY = 16;
    public static final int EEXIST = 17;
    public static final int EXDEV = 18;
    public static final int ENODEV = 19;
    public static final int ENOTDIR = 20;
    public static final int EISDIR = 21;
    public static final int EINVAL = 22;
    public static final int ENFILE = 23;
    public static final int EMFILE = 24;
    public static final int ENOTTY = 25;
    public static final int ETXTBSY = 26;
    public static final int EFBIG = 27;
    public static final int ENOSPC = 28;
    public static final int ESPIPE = 29;
    public static final int EROFS = 30;
    public static final int EMLINK = 31;
    public static final int EPIPE = 32;
    public static final int EDOM = 33;
    public static final int ERANGE = 34;
    public static final int ENOMSG = 35;
    public static final int EIDRM = 36;
    public static final int ECHRNG = 37;
    public static final int EL2NSYNC = 38;
    public static final int EL3HLT = 39;
    public static final int EL3RST = 40;
    public static final int ELNRNG = 41;
    public static final int EUNATCH = 42;
    public static final int ENOCSI = 43;
    public static final int EL2HLT = 44;
    public static final int EDEADLK = 45;
    public static final int ENOLCK = 46;
    public static final int EBADE = 50;
    public static final int EBADR = 51;
    public static final int EXFULL = 52;
    public static final int ENOANO = 53;
    public static final int EBADRQC = 54;
    public static final int EBADSLT = 55;
    public static final int EDEADLOCK = 56;
    public static final int EBFONT = 57;
    public static final int ENOSTR = 60;
    public static final int ENODATA = 61;
    public static final int ETIME = 62;
    public static final int ENOSR = 63;
    public static final int ENONET = 64;
    public static final int ENOPKG = 65;
    public static final int EREMOTE = 66;
    public static final int ENOLINK = 67;
    public static final int EADV = 68;
    public static final int ESRMNT = 69;
    public static final int ECOMM = 70;
    public static final int EPROTO = 71;
    public static final int EMULTIHOP = 74;
    public static final int ELBIN = 75;
    public static final int EDOTDOT = 76;
    public static final int EBADMSG = 77;
    public static final int EFTYPE = 79;
    public static final int ENOTUNIQ = 80;
    public static final int EBADFD = 81;
    public static final int EREMCHG = 82;
    public static final int ELIBACC = 83;
    public static final int ELIBBAD = 84;
    public static final int ELIBSCN = 85;
    public static final int ELIBMAX = 86;
    public static final int ELIBEXEC = 87;
    public static final int ENOSYS = 88;
    public static final int ENMFILE = 89;
    public static final int ENOTEMPTY = 90;
    public static final int ENAMETOOLONG = 91;
    public static final int ELOOP = 92;
    public static final int EOPNOTSUPP = 95;
    public static final int EPFNOSUPPORT = 96;
    public static final int ECONNRESET = 104;
    public static final int ENOBUFS = 105;
    public static final int EAFNOSUPPORT = 106;
    public static final int EPROTOTYPE = 107;
    public static final int ENOTSOCK = 108;
    public static final int ENOPROTOOPT = 109;
    public static final int ESHUTDOWN = 110;
    public static final int ECONNREFUSED = 111;
    public static final int EADDRINUSE = 112;
    public static final int ECONNABORTED = 113;
    public static final int ENETUNREACH = 114;
    public static final int ENETDOWN = 115;
    public static final int ETIMEDOUT = 116;
    public static final int EHOSTDOWN = 117;
    public static final int EHOSTUNREACH = 118;
    public static final int EINPROGRESS = 119;
    public static final int EALREADY = 120;
    public static final int EDESTADDRREQ = 121;
    public static final int EMSGSIZE = 122;
    public static final int EPROTONOSUPPORT = 123;
    public static final int ESOCKTNOSUPPORT = 124;
    public static final int EADDRNOTAVAIL = 125;
    public static final int ENETRESET = 126;
    public static final int EISCONN = 127;
    public static final int ENOTCONN = 128;
    public static final int ETOOMANYREFS = 129;
    public static final int EPROCLIM = 130;
    public static final int EUSERS = 131;
    public static final int EDQUOT = 132;
    public static final int ESTALE = 133;
    public static final int ENOTSUP = 134;
    public static final int ENOMEDIUM = 135;
    public static final int ENOSHARE = 136;
    public static final int ECASECLASH = 137;
    public static final int EILSEQ = 138;
    public static final int EOVERFLOW = 139;
    public static final int __ELASTERROR = 2000;
    public static final int F_OK = 0;
    public static final int R_OK = 4;
    public static final int W_OK = 2;
    public static final int X_OK = 1;
    public static final int SEEK_SET = 0;
    public static final int SEEK_CUR = 1;
    public static final int SEEK_END = 2;
    public static final int STDIN_FILENO = 0;
    public static final int STDOUT_FILENO = 1;
    public static final int STDERR_FILENO = 2;
    public static final int _SC_ARG_MAX = 0;
    public static final int _SC_CHILD_MAX = 1;
    public static final int _SC_CLK_TCK = 2;
    public static final int _SC_NGROUPS_MAX = 3;
    public static final int _SC_OPEN_MAX = 4;
    public static final int _SC_JOB_CONTROL = 5;
    public static final int _SC_SAVED_IDS = 6;
    public static final int _SC_VERSION = 7;
    public static final int _SC_PAGESIZE = 8;
    public static final int _SC_NPROCESSORS_CONF = 9;
    public static final int _SC_NPROCESSORS_ONLN = 10;
    public static final int _SC_PHYS_PAGES = 11;
    public static final int _SC_AVPHYS_PAGES = 12;
    public static final int _SC_MQ_OPEN_MAX = 13;
    public static final int _SC_MQ_PRIO_MAX = 14;
    public static final int _SC_RTSIG_MAX = 15;
    public static final int _SC_SEM_NSEMS_MAX = 16;
    public static final int _SC_SEM_VALUE_MAX = 17;
    public static final int _SC_SIGQUEUE_MAX = 18;
    public static final int _SC_TIMER_MAX = 19;
    public static final int _SC_TZNAME_MAX = 20;
    public static final int _SC_ASYNCHRONOUS_IO = 21;
    public static final int _SC_FSYNC = 22;
    public static final int _SC_MAPPED_FILES = 23;
    public static final int _SC_MEMLOCK = 24;
    public static final int _SC_MEMLOCK_RANGE = 25;
    public static final int _SC_MEMORY_PROTECTION = 26;
    public static final int _SC_MESSAGE_PASSING = 27;
    public static final int _SC_PRIORITIZED_IO = 28;
    public static final int _SC_REALTIME_SIGNALS = 29;
    public static final int _SC_SEMAPHORES = 30;
    public static final int _SC_SHARED_MEMORY_OBJECTS = 31;
    public static final int _SC_SYNCHRONIZED_IO = 32;
    public static final int _SC_TIMERS = 33;
    public static final int _SC_AIO_LISTIO_MAX = 34;
    public static final int _SC_AIO_MAX = 35;
    public static final int _SC_AIO_PRIO_DELTA_MAX = 36;
    public static final int _SC_DELAYTIMER_MAX = 37;
    public static final int _SC_THREAD_KEYS_MAX = 38;
    public static final int _SC_THREAD_STACK_MIN = 39;
    public static final int _SC_THREAD_THREADS_MAX = 40;
    public static final int _SC_TTY_NAME_MAX = 41;
    public static final int _SC_THREADS = 42;
    public static final int _SC_THREAD_ATTR_STACKADDR = 43;
    public static final int _SC_THREAD_ATTR_STACKSIZE = 44;
    public static final int _SC_THREAD_PRIORITY_SCHEDULING = 45;
    public static final int _SC_THREAD_PRIO_INHERIT = 46;
    public static final int _SC_THREAD_PRIO_PROTECT = 47;
    public static final int _SC_THREAD_PROCESS_SHARED = 48;
    public static final int _SC_THREAD_SAFE_FUNCTIONS = 49;
    public static final int _SC_GETGR_R_SIZE_MAX = 50;
    public static final int _SC_GETPW_R_SIZE_MAX = 51;
    public static final int _SC_LOGIN_NAME_MAX = 52;
    public static final int _SC_THREAD_DESTRUCTOR_ITERATIONS = 53;
    public static final int _SC_STREAM_MAX = 100;
    public static final int _SC_PRIORITY_SCHEDULING = 101;
    public static final int _PC_LINK_MAX = 0;
    public static final int _PC_MAX_CANON = 1;
    public static final int _PC_MAX_INPUT = 2;
    public static final int _PC_NAME_MAX = 3;
    public static final int _PC_PATH_MAX = 4;
    public static final int _PC_PIPE_BUF = 5;
    public static final int _PC_CHOWN_RESTRICTED = 6;
    public static final int _PC_NO_TRUNC = 7;
    public static final int _PC_VDISABLE = 8;
    public static final int _PC_ASYNC_IO = 9;
    public static final int _PC_PRIO_IO = 10;
    public static final int _PC_SYNC_IO = 11;
    public static final int _PC_POSIX_PERMISSIONS = 90;
    public static final int _PC_POSIX_SECURITY = 91;
    public static final int MAXPATHLEN = 1024;
    public static final int ARG_MAX = 65536;
    public static final int CHILD_MAX = 40;
    public static final int LINK_MAX = 32767;
    public static final int MAX_CANON = 255;
    public static final int MAX_INPUT = 255;
    public static final int NAME_MAX = 255;
    public static final int NGROUPS_MAX = 16;
    public static final int OPEN_MAX = 64;
    public static final int PATH_MAX = 1024;
    public static final int PIPE_BUF = 512;
    public static final int IOV_MAX = 1024;
    public static final int BC_BASE_MAX = 99;
    public static final int BC_DIM_MAX = 2048;
    public static final int BC_SCALE_MAX = 99;
    public static final int BC_STRING_MAX = 1000;
    public static final int COLL_WEIGHTS_MAX = 0;
    public static final int EXPR_NEST_MAX = 32;
    public static final int LINE_MAX = 2048;
    public static final int RE_DUP_MAX = 255;
    public static final int CTL_MAXNAME = 12;
    public static final int CTL_UNSPEC = 0;
    public static final int CTL_KERN = 1;
    public static final int CTL_VM = 2;
    public static final int CTL_VFS = 3;
    public static final int CTL_NET = 4;
    public static final int CTL_DEBUG = 5;
    public static final int CTL_HW = 6;
    public static final int CTL_MACHDEP = 7;
    public static final int CTL_USER = 8;
    public static final int CTL_P1003_1B = 9;
    public static final int CTL_MAXID = 10;
    public static final int KERN_OSTYPE = 1;
    public static final int KERN_OSRELEASE = 2;
    public static final int KERN_OSREV = 3;
    public static final int KERN_VERSION = 4;
    public static final int KERN_MAXVNODES = 5;
    public static final int KERN_MAXPROC = 6;
    public static final int KERN_MAXFILES = 7;
    public static final int KERN_ARGMAX = 8;
    public static final int KERN_SECURELVL = 9;
    public static final int KERN_HOSTNAME = 10;
    public static final int KERN_HOSTID = 11;
    public static final int KERN_CLOCKRATE = 12;
    public static final int KERN_VNODE = 13;
    public static final int KERN_PROC = 14;
    public static final int KERN_FILE = 15;
    public static final int KERN_PROF = 16;
    public static final int KERN_POSIX1 = 17;
    public static final int KERN_NGROUPS = 18;
    public static final int KERN_JOB_CONTROL = 19;
    public static final int KERN_SAVED_IDS = 20;
    public static final int KERN_BOOTTIME = 21;
    public static final int KERN_NISDOMAINNAME = 22;
    public static final int KERN_UPDATEINTERVAL = 23;
    public static final int KERN_OSRELDATE = 24;
    public static final int KERN_NTP_PLL = 25;
    public static final int KERN_BOOTFILE = 26;
    public static final int KERN_MAXFILESPERPROC = 27;
    public static final int KERN_MAXPROCPERUID = 28;
    public static final int KERN_DUMPDEV = 29;
    public static final int KERN_IPC = 30;
    public static final int KERN_DUMMY = 31;
    public static final int KERN_PS_STRINGS = 32;
    public static final int KERN_USRSTACK = 33;
    public static final int KERN_LOGSIGEXIT = 34;
    public static final int KERN_MAXID = 35;
    public static final int KERN_PROC_ALL = 0;
    public static final int KERN_PROC_PID = 1;
    public static final int KERN_PROC_PGRP = 2;
    public static final int KERN_PROC_SESSION = 3;
    public static final int KERN_PROC_TTY = 4;
    public static final int KERN_PROC_UID = 5;
    public static final int KERN_PROC_RUID = 6;
    public static final int KERN_PROC_ARGS = 7;
    public static final int KIPC_MAXSOCKBUF = 1;
    public static final int KIPC_SOCKBUF_WASTE = 2;
    public static final int KIPC_SOMAXCONN = 3;
    public static final int KIPC_MAX_LINKHDR = 4;
    public static final int KIPC_MAX_PROTOHDR = 5;
    public static final int KIPC_MAX_HDR = 6;
    public static final int KIPC_MAX_DATALEN = 7;
    public static final int KIPC_MBSTAT = 8;
    public static final int KIPC_NMBCLUSTERS = 9;
    public static final int HW_MACHINE = 1;
    public static final int HW_MODEL = 2;
    public static final int HW_NCPU = 3;
    public static final int HW_BYTEORDER = 4;
    public static final int HW_PHYSMEM = 5;
    public static final int HW_USERMEM = 6;
    public static final int HW_PAGESIZE = 7;
    public static final int HW_DISKNAMES = 8;
    public static final int HW_DISKSTATS = 9;
    public static final int HW_FLOATINGPT = 10;
    public static final int HW_MACHINE_ARCH = 11;
    public static final int HW_MAXID = 12;
    public static final int USER_CS_PATH = 1;
    public static final int USER_BC_BASE_MAX = 2;
    public static final int USER_BC_DIM_MAX = 3;
    public static final int USER_BC_SCALE_MAX = 4;
    public static final int USER_BC_STRING_MAX = 5;
    public static final int USER_COLL_WEIGHTS_MAX = 6;
    public static final int USER_EXPR_NEST_MAX = 7;
    public static final int USER_LINE_MAX = 8;
    public static final int USER_RE_DUP_MAX = 9;
    public static final int USER_POSIX2_VERSION = 10;
    public static final int USER_POSIX2_C_BIND = 11;
    public static final int USER_POSIX2_C_DEV = 12;
    public static final int USER_POSIX2_CHAR_TERM = 13;
    public static final int USER_POSIX2_FORT_DEV = 14;
    public static final int USER_POSIX2_FORT_RUN = 15;
    public static final int USER_POSIX2_LOCALEDEF = 16;
    public static final int USER_POSIX2_SW_DEV = 17;
    public static final int USER_POSIX2_UPE = 18;
    public static final int USER_STREAM_MAX = 19;
    public static final int USER_TZNAME_MAX = 20;
    public static final int USER_MAXID = 21;
    public static final int CTL_P1003_1B_ASYNCHRONOUS_IO = 1;
    public static final int CTL_P1003_1B_MAPPED_FILES = 2;
    public static final int CTL_P1003_1B_MEMLOCK = 3;
    public static final int CTL_P1003_1B_MEMLOCK_RANGE = 4;
    public static final int CTL_P1003_1B_MEMORY_PROTECTION = 5;
    public static final int CTL_P1003_1B_MESSAGE_PASSING = 6;
    public static final int CTL_P1003_1B_PRIORITIZED_IO = 7;
    public static final int CTL_P1003_1B_PRIORITY_SCHEDULING = 8;
    public static final int CTL_P1003_1B_REALTIME_SIGNALS = 9;
    public static final int CTL_P1003_1B_SEMAPHORES = 10;
    public static final int CTL_P1003_1B_FSYNC = 11;
    public static final int CTL_P1003_1B_SHARED_MEMORY_OBJECTS = 12;
    public static final int CTL_P1003_1B_SYNCHRONIZED_IO = 13;
    public static final int CTL_P1003_1B_TIMERS = 14;
    public static final int CTL_P1003_1B_AIO_LISTIO_MAX = 15;
    public static final int CTL_P1003_1B_AIO_MAX = 16;
    public static final int CTL_P1003_1B_AIO_PRIO_DELTA_MAX = 17;
    public static final int CTL_P1003_1B_DELAYTIMER_MAX = 18;
    public static final int CTL_P1003_1B_MQ_OPEN_MAX = 19;
    public static final int CTL_P1003_1B_PAGESIZE = 20;
    public static final int CTL_P1003_1B_RTSIG_MAX = 21;
    public static final int CTL_P1003_1B_SEM_NSEMS_MAX = 22;
    public static final int CTL_P1003_1B_SEM_VALUE_MAX = 23;
    public static final int CTL_P1003_1B_SIGQUEUE_MAX = 24;
    public static final int CTL_P1003_1B_TIMER_MAX = 25;
    public static final int CTL_P1003_1B_MAXID = 26;
    public static final int F_UNLKSYS = 4;
    public static final int F_CNVT = 12;
    public static final int F_SETFD = 2;
    public static final int F_SETFL = 4;
    public static final int F_SETLK = 8;
    public static final int F_SETOWN = 6;
    public static final int F_RDLCK = 1;
    public static final int F_WRLCK = 2;
    public static final int F_SETLKW = 9;
    public static final int F_GETFD = 1;
    public static final int F_DUPFD = 0;
    public static final int O_WRONLY = 1;
    public static final int F_RSETLKW = 13;
    public static final int O_RDWR = 2;
    public static final int F_RGETLK = 10;
    public static final int O_RDONLY = 0;
    public static final int F_UNLCK = 3;
    public static final int F_GETOWN = 5;
    public static final int F_RSETLK = 11;
    public static final int F_GETFL = 3;
    public static final int F_GETLK = 7;
}
