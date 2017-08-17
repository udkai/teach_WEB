DROP TABLE IF EXISTS `t_teach_category`;
CREATE TABLE `t_teach_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `level` tinyint(4) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `sort_order` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

INSERT INTO t_sys_module VALUES ('158', '知识体系管理', 'TeachCategory', '153', 'teach/category/index.htm', 'main', '', '5', null);

INSERT INTO t_sys_function VALUES ('560', '158', '新增', 'TeachCategoryAdd', 'teach/category/add.htm', '1', '新增', '1');
INSERT INTO t_sys_function VALUES ('561', '158', '修改', 'TeachCategoryUpdate', 'teach/category/update.htm', '1', '修改', '2');
INSERT INTO t_sys_function VALUES ('562', '158', '删除', 'TeachCategoryDelete', 'teach/category/delete.htm', '1', '删除', '3');

