DROP TABLE IF EXISTS `t_teach_category`;
CREATE TABLE `t_teach_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `level` tinyint(4) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `sort_order` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

INSERT INTO t_sys_module VALUES ('158', '֪ʶ��ϵ����', 'TeachCategory', '153', 'teach/category/index.htm', 'main', '', '5', null);

INSERT INTO t_sys_function VALUES ('560', '158', '����', 'TeachCategoryAdd', 'teach/category/add.htm', '1', '����', '1');
INSERT INTO t_sys_function VALUES ('561', '158', '�޸�', 'TeachCategoryUpdate', 'teach/category/update.htm', '1', '�޸�', '2');
INSERT INTO t_sys_function VALUES ('562', '158', 'ɾ��', 'TeachCategoryDelete', 'teach/category/delete.htm', '1', 'ɾ��', '3');

