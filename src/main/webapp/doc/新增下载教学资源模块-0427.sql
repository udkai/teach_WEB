ALTER TABLE `t_teach_downloadlog`
CHANGE COLUMN `download_usre_id` `download_user_id`  int(11) NULL DEFAULT NULL AFTER `download_user_name`;
INSERT INTO t_sys_module VALUES ('311', '教学资源下载', 'TeachDownloadRep', '303', 'teach/downloadRepository/index.htm', 'main', '', '5', null);
INSERT INTO t_sys_function VALUES ('602', '311', '下载', 'TeachDownloadRepDown', 'teach/downloadRepository/download.htm', '1', '下载', '1');


