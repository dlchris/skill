


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `sys_permission` */

CREATE TABLE `sys_permission` (
  `id` bigint(20) NOT NULL COMMENT '����',
  `name` varchar(128) NOT NULL COMMENT '��Դ����',
  `type` varchar(32) NOT NULL COMMENT '��Դ���ͣ�menu,button,',
  `url` varchar(128) DEFAULT NULL COMMENT '����url��ַ',
  `percode` varchar(128) DEFAULT NULL COMMENT 'Ȩ�޴����ַ���',
  `parentid` bigint(20) DEFAULT NULL COMMENT '�����id',
  `parentids` varchar(128) DEFAULT NULL COMMENT '�����id�б�',
  `sortstring` varchar(128) DEFAULT NULL COMMENT '�����',
  `available` char(1) DEFAULT NULL COMMENT '�Ƿ����,1�����ã�0������',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `sys_role` */

CREATE TABLE `sys_role` (
  `id` varchar(36) NOT NULL,
  `name` varchar(128) NOT NULL,
  `available` char(1) DEFAULT NULL COMMENT '�Ƿ����,1�����ã�0������',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `sys_role_permission` */

CREATE TABLE `sys_role_permission` (
  `id` varchar(36) NOT NULL,
  `sys_role_id` varchar(32) NOT NULL COMMENT '��ɫid',
  `sys_permission_id` varchar(32) NOT NULL COMMENT 'Ȩ��id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `sys_user` */

CREATE TABLE `sys_user` (
  `id` varchar(36) NOT NULL COMMENT '����',
  `usercode` varchar(32) NOT NULL COMMENT '�˺�',
  `username` varchar(64) NOT NULL COMMENT '����',
  `password` varchar(32) NOT NULL COMMENT '����',
  `salt` varchar(64) DEFAULT NULL COMMENT '��',
  `locked` char(1) DEFAULT NULL COMMENT '�˺��Ƿ�������1��������0δ����',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `sys_user_role` */

CREATE TABLE `sys_user_role` (
  `id` varchar(36) NOT NULL,
  `sys_user_id` varchar(32) NOT NULL,
  `sys_role_id` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
