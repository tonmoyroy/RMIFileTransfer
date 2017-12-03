# RMIFileTransfer

USAGE:

To login-
```login localhost username password```

To upload-
```upload filename.extension```

**Note - File should be in the root directory path of the project

To download-
```download filename.extension```

**Note - Current implementation doesn't support filename suggestion from server. 
Therefore, the download filename should be exaclty same as it was uploaded.

To See Files Uploaded in Server-
```showfiles```

**Note - Without login client cannot upload/download any file.


# FOR STORAGE IN DATABASE

```Schema name - jdbc
Username - root
Password - empty```

```
MySQL Command - CREATE DATABASE `jdbc`;
```
# Tables

**USER TABLE
```
CREATE TABLE `users` (
  `userid` double NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
```
**FILES TABLE
```
CREATE TABLE `files` (
  `userid` double NOT NULL,
  `filename` varchar(100) NOT NULL,
  `filepath` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
```
