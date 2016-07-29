# 20160329 begin
# chenxu
# 创建学生家长手机号和用户的关系表
DROP TABLE IF EXISTS u_user_mobile_history;
CREATE TABLE `u_user_mobile_history` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_id` INT(11) NOT NULL,
  `mobile` VARCHAR(11) COLLATE utf8_bin NOT NULL,
  `create_time` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# 菏泽市
INSERT INTO `u_distcd` (`dist_cd`, `p_dist_cd`, `prov_name`, `city_name`, `cnty_name`, `status_cd`, `lev_cd`) VALUES ('371700', '370000', '山东省', '菏泽市', NULL, '1', 2);
INSERT INTO `u_distcd` (`dist_cd`, `p_dist_cd`, `prov_name`, `city_name`, `cnty_name`, `status_cd`, `lev_cd`) VALUES ('371701', '371700', '山东省', '菏泽市', '市直', '1', 3);
INSERT INTO `u_distcd` (`dist_cd`, `p_dist_cd`, `prov_name`, `city_name`, `cnty_name`, `status_cd`, `lev_cd`) VALUES ('371702', '371700', '山东省', '菏泽市', '牡丹区', '1', 3);
INSERT INTO `u_distcd` (`dist_cd`, `p_dist_cd`, `prov_name`, `city_name`, `cnty_name`, `status_cd`, `lev_cd`) VALUES ('371721', '371700', '山东省', '菏泽市', '曹县', '1', 3);
INSERT INTO `u_distcd` (`dist_cd`, `p_dist_cd`, `prov_name`, `city_name`, `cnty_name`, `status_cd`, `lev_cd`) VALUES ('371722', '371700', '山东省', '菏泽市', '单县', '1', 3);
INSERT INTO `u_distcd` (`dist_cd`, `p_dist_cd`, `prov_name`, `city_name`, `cnty_name`, `status_cd`, `lev_cd`) VALUES ('371723', '371700', '山东省', '菏泽市', '成武县', '1', 3);
INSERT INTO `u_distcd` (`dist_cd`, `p_dist_cd`, `prov_name`, `city_name`, `cnty_name`, `status_cd`, `lev_cd`) VALUES ('371724', '371700', '山东省', '菏泽市', '巨野县', '1', 3);
INSERT INTO `u_distcd` (`dist_cd`, `p_dist_cd`, `prov_name`, `city_name`, `cnty_name`, `status_cd`, `lev_cd`) VALUES ('371725', '371700', '山东省', '菏泽市', '郓城县', '1', 3);
INSERT INTO `u_distcd` (`dist_cd`, `p_dist_cd`, `prov_name`, `city_name`, `cnty_name`, `status_cd`, `lev_cd`) VALUES ('371726', '371700', '山东省', '菏泽市', '鄄城县', '1', 3);
INSERT INTO `u_distcd` (`dist_cd`, `p_dist_cd`, `prov_name`, `city_name`, `cnty_name`, `status_cd`, `lev_cd`) VALUES ('371727', '371700', '山东省', '菏泽市', '定陶县', '1', 3);
INSERT INTO `u_distcd` (`dist_cd`, `p_dist_cd`, `prov_name`, `city_name`, `cnty_name`, `status_cd`, `lev_cd`) VALUES ('371728', '371700', '山东省', '菏泽市', '东明县', '1', 3);

ALTER TABLE `u_school` ADD prefix_code VARCHAR(10) DEFAULT NULL COMMENT "学校前缀码";
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371702', '牡丹区第二十二初级中学', '0', '001hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371702', '牡丹区黄罡镇侯集中学', '0', '002hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371702', '牡丹区皇镇乡初级中学', '0', '003hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371702', '牡丹区王浩屯镇初级中学', '0', '004hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371702', '牡丹区牡丹办事处中心初级中学', '0', '005hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371702', '牡丹区马岭岗镇二郎庙初级中学', '0', '006hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371702', '牡丹区安兴镇初级中学', '0', '007hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371702', '牡丹区都司镇第十一中学', '0', '008hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371702', '牡丹区吕陵镇中心初级中学', '0', '009hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371702', '牡丹区第二十三中学', '0', '010hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371702', '牡丹区何楼镇东方红初级中学', '0', '011hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371702', '牡丹区马岭岗镇中心初级中学', '0', '012hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371702', '牡丹区何楼镇中心初级中学', '0', '013hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371702', '牡丹区李村镇中心初级中学', '0', '014hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371702', '牡丹区吕陵镇贾坊初级中学', '0', '015hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371702', '牡丹区小留镇中心初级中学', '0', '016hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371702', '牡丹区第十中学', '0', '017hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371702', '牡丹区何楼镇金堤初级中学', '0', '018hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371702', '牡丹区第二十一初级中学', '0', '019hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371702', '牡丹区李村镇李庄初级中学', '0', '020hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371702', '牡丹区胡集乡民族中学', '0', '021hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371702', '牡丹区实验中学', '0', '022hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371702', '牡丹区黄堽镇中心中学', '0', '023hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371702', '牡丹区胡集镇初级中心中学', '0', '024hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371702', '牡丹区北城中学', '0', '025hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371702', '牡丹区吴店镇中心初级中学', '0', '026hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371702', '牡丹区沙土镇新兴初级中学', '0', '027hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371702', '牡丹区沙土镇中心初级中学', '0', '028hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371702', '牡丹区高庄镇初级中学', '0', '029hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371702', '牡丹区大黄集镇初级中学', '0', '030hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371702', '牡丹区万福办事处登禹中学', '0', '031hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371702', '牡丹区牡丹办事处庞王初级中学', '0', '032hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371702', '牡丹区第五中学', '0', '033hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371702', '菏泽二中', '0', '034hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371702', '菏泽三中', '0', '035hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371702', '牡丹区解元集初级中学', '0', '036hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371701', '菏泽市开发区广州路中学', '0', '037hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371701', '菏泽市开发区佃户屯办事处中学', '0', '038hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371701', '菏泽市实验中学', '0', '039hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371701', '菏泽一中', '0', '040hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371721', '曹县第一初级中学', '0', '041hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371721', '曹县古营集镇中学', '0', '042hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371721', '曹县曹城镇中学', '0', '043hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371721', '曹县常乐集乡中学', '0', '044hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371721', '曹县朱洪庙乡中学', '0', '045hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371721', '曹县桃源集镇大寨中学', '0', '046hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371721', '曹县桃源集镇中学', '0', '047hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371721', '曹县青菏街道办事处中学', '0', '048hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371721', '曹县砖庙镇中学', '0', '049hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371721', '曹县孙老家镇中学', '0', '050hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371721', '曹县魏湾镇中学', '0', '051hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371721', '曹县郑庄镇中学', '0', '052hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371721', '曹县梁堤头镇中学', '0', '053hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371721', '曹县仵楼乡中学', '0', '054hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371721', '曹县实验中学', '0', '055hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371721', '曹县王集镇中学', '0', '056hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371721', '曹县邵庄镇中学', '0', '057hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371721', '曹县楼庄乡中学', '0', '058hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371721', '曹县倪集乡中学', '0', '059hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371721', '曹县倪集乡第二中学', '0', '060hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371721', '曹县青岗集镇中学', '0', '061hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371721', '曹县苏集镇孟楼中学', '0', '062hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371721', '曹县苏集镇中学', '0', '063hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371721', '曹县韩集镇中学', '0', '064hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371721', '曹县青堌集镇南李集中学', '0', '065hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371721', '曹县青堌集镇中学', '0', '066hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371721', '曹县磐石办事处回民中学', '0', '067hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371721', '曹县侯集回族镇中学', '0', '068hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371721', '曹县庄寨镇中学', '0', '069hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371721', '曹县庄寨镇白茅中学', '0', '070hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371721', '曹县闫店楼镇中学', '0', '071hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371721', '曹县安才楼镇中学', '0', '072hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371721', '曹县安才楼镇火神台中学', '0', '073hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371721', '曹县大集乡中学', '0', '074hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371721', '曹县梁堤头职业高中', '0', '075hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371721', '曹县普连集镇中学', '0', '076hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371721', '曹县侯集回族镇回民中学', '0', '077hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371721', '曹县魏湾镇申庄寨中学', '0', '078hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371721', '曹县苏集镇龚楼中学', '0', '079hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371721', '曹县一中', '0', '080hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371721', '曹县普通高中', '0', '081hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371721', '曹县砖庙镇西田集中学', '0', '082hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371723', '成武县实验中学', '0', '083hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371723', '成武县文亭街道办事处第一中学', '0', '084hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371723', '成武县永昌街道办事处中心中学', '0', '085hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371723', '成武县永昌街道办事处振兴中学', '0', '086hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371723', '成武县永昌街道办事处郜鼎中学', '0', '087hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371723', '成武县南鲁集镇初级中学', '0', '088hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371723', '成武县九女镇智楼中学', '0', '089hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371723', '成武县苟村集镇初级中学', '0', '090hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371723', '成武县大田集镇桃花寺初级中学', '0', '091hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371723', '成武县白浮图镇初级中学', '0', '092hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371723', '成武县张楼乡初级中学', '0', '093hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371723', '成武县张楼乡第二中学', '0', '094hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371723', '成武县汶上集镇宝峰初级中学', '0', '095hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371723', '成武县孙寺镇初级中学', '0', '096hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371723', '成武县伯乐集镇初级中学', '0', '097hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371723', '成武县大田集镇初级中学', '0', '098hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371723', '成武县党集乡初级中学', '0', '099hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371723', '成武县汶上集镇初级中学', '0', '100hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371723', '成武县天宫庙镇康集中学', '0', '101hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371723', '成武县天宫庙镇初级中学', '0', '102hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371723', '成武县成武一中', '0', '103hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371723', '成武县成武二中', '0', '104hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371723', '成武县九女集镇初级中学', '0', '105hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371722', '单县人民路中学', '0', '106hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371722', '单县北城第一初级中学', '0', '107hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371722', '单县南城中学', '0', '108hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371722', '单县北城三中', '0', '109hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371722', '单县实验中学', '0', '110hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371722', '单县第二实验中学', '0', '111hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371722', '单县东城初级中学', '0', '112hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371722', '单县谢集镇谢集初级中学', '0', '113hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371722', '单县莱河镇莱河初级中学', '0', '114hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371722', '单县莱河镇丁楼初级中学', '0', '115hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371722', '单县郭村初级中学', '0', '116hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371722', '单县经济开发区实验中学', '0', '117hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371722', '单县郭村镇大李海初级中学', '0', '118hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371722', '单县高老家乡高老家初级中学', '0', '119hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371722', '单县高老家乡曹叵集初级中学', '0', '120hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371722', '单县曹庄乡初级中学', '0', '121hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371722', '单县高韦庄镇中学', '0', '122hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371722', '单县高韦庄职业中学', '0', '123hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371722', '单县浮岗镇浮岗初级中学', '0', '124hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371722', '单县浮岗镇李新集初级中学', '0', '125hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371722', '单县黄岗镇花园中学', '0', '126hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371722', '单县黄岗镇黄岗初级中学', '0', '127hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371722', '单县杨楼镇杨楼初级中学', '0', '128hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371722', '单县杨楼镇孟寨初级中学', '0', '129hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371722', '单县龙王庙镇罗庄初级中学', '0', '130hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371722', '单县龙王庙中学', '0', '131hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371722', '单县蔡堂镇初级中学', '0', '132hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371722', '单县蔡堂职业中学', '0', '133hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371722', '单县终兴镇王小庄中学', '0', '134hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371722', '单县终兴初级中学', '0', '135hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371722', '单县张集镇张集初级中学', '0', '136hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371722', '单县张集镇黄堆初级中学', '0', '137hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371722', '单县朱集镇中学', '0', '138hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371722', '单县李田楼镇初级中学', '0', '139hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371722', '单县时楼镇时楼初级中学', '0', '140hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371722', '单县徐寨镇初级中学', '0', '141hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371722', '单县一中', '0', '142hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371722', '单县二中', '0', '143hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371722', '单县五中', '0', '144hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371722', '单县李新庄镇中学', '0', '145hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371727', '定陶县陈集镇第二中学', '0', '146hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371727', '定陶县马集镇第二中学', '0', '147hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371727', '定陶县马集镇中学', '0', '148hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371727', '定陶县张湾镇中学', '0', '149hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371727', '定陶县黄店镇中学', '0', '150hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371727', '定陶县孟海镇中学', '0', '151hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371727', '定陶县实验中学', '0', '152hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371727', '定陶县定陶镇中学', '0', '153hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371727', '定陶县冉堌镇第二中学', '0', '154hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371727', '定陶县南王店乡中学', '0', '155hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371727', '定陶县半堤镇中学', '0', '156hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371727', '定陶县杜堂镇中学', '0', '157hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371727', '定陶县仿山镇中学', '0', '158hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371727', '定陶一中', '0', '159hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371727', '定陶二中', '0', '160hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371727', '山大附中实验学校', '0', '161hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371727', '定陶县陈集镇中学', '0', '162hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371728', '东明县小井乡第二初级中学', '0', '163hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371728', '东明县沙窝乡第二初级中学', '0', '164hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371728', '东明县焦园乡实验学校', '0', '165hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371728', '东明县城关镇第一初级中学', '0', '166hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371728', '东明县城关镇第二初级中学', '0', '167hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371728', '东明县第一初级中学', '0', '168hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371728', '东明县第二初级中学', '0', '169hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371728', '东明县东明集第一初级中学', '0', '170hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371728', '东明县东明集镇第二初级中学', '0', '171hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371728', '东明县陆圈镇第一初级中学', '0', '172hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371728', '东明县陆圈镇第二初级中学', '0', '173hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371728', '东明县马头镇初级中学', '0', '174hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371728', '东明县三春集初级中学', '0', '175hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371728', '东明县刘楼镇第一初级中学', '0', '176hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371728', '东明县大屯镇初级中学', '0', '177hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371728', '东明县大屯镇成龙中学', '0', '178hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371728', '东明县焦园乡些初级中学', '0', '179hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371728', '东明县长兴集乡第一初级中学', '0', '180hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371728', '东明县长兴集乡第二初级中学', '0', '181hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371728', '东明县沙窝乡第一初级中学', '0', '182hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371728', '东明县小井乡初级中学', '0', '183hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371728', '东明县武胜桥乡初级中学', '0', '184hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371728', '东明县实验中学', '0', '185hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371728', '东明一中', '0', '186hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371728', '东明县菜园集乡初级中学', '0', '187hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371724', '巨野县第二中学', '0', '188hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371724', '巨野县柳林镇第一中学', '0', '189hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371724', '巨野县大谢集镇第一中学', '0', '190hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371724', '巨野县大谢集镇第二中学', '0', '191hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371724', '巨野县麒麟镇第一中学', '0', '192hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371724', '巨野县麒麟镇第二中学', '0', '193hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371724', '巨野县核桃园镇中学', '0', '194hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371724', '巨野县独山镇第一中学', '0', '195hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371724', '巨野县独山镇第二中学', '0', '196hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371724', '巨野县万丰镇第一中学', '0', '197hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371724', '巨野县万丰镇第二中学', '0', '198hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371724', '巨野县田庄镇第二中学', '0', '199hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371724', '巨野县田庄镇第一中学', '0', '200hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371724', '巨野县龙固镇第二中学', '0', '201hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371724', '巨野县董官屯镇第一中学', '0', '202hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371724', '巨野县董官屯镇第二中学', '0', '203hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371724', '巨野县龙固镇第一中学', '0', '204hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371724', '巨野县经济技术开发区第一中学', '0', '205hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371724', '巨野县太平镇中学', '0', '206hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371724', '巨野县大义镇第二中学', '0', '207hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371724', '巨野县大义镇第一中学', '0', '208hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371724', '巨野县营里镇中学', '0', '209hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371724', '巨野县永丰街道办事处中学', '0', '210hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371724', '巨野县柳林镇第二中学', '0', '211hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371724', '巨野县陶庙镇中学', '0', '212hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371724', '巨野县十二中学', '0', '213hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371724', '巨野县章缝镇第一中学', '0', '214hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371724', '巨野县田桥镇中学', '0', '215hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371724', '巨野一中', '0', '216hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371724', '巨野县实验中学', '0', '217hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371724', '巨野县高级中学', '0', '218hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371726', '鄄城县郑营镇郑营中学', '0', '219hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371726', '鄄城县郑营镇鲁王仓中学', '0', '220hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371726', '鄄城县红船镇中学', '0', '221hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371726', '鄄城县闫什镇闫什中学', '0', '222hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371726', '鄄城县闫什镇张志门中学', '0', '223hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371726', '鄄城县富春乡富春中学', '0', '224hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371726', '鄄城县大埝乡大埝中学', '0', '225hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371726', '鄄城县麻寨中学', '0', '226hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371726', '鄄城县彭楼中学', '0', '227hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371726', '鄄城县董口镇双庙中学', '0', '228hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371726', '鄄城县董口镇董口中学', '0', '229hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371726', '鄄城县左营乡左营中学', '0', '230hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371726', '鄄城县左营乡陈良中学', '0', '231hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371726', '鄄城县旧城镇旧城中学', '0', '232hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371726', '鄄城县鄄城镇中学', '0', '233hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371726', '鄄城县吉山镇中学', '0', '234hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371726', '鄄城县吉山镇宋楼中学', '0', '235hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371726', '鄄城县凤凰镇乡凤凰乡中学', '0', '236hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371726', '鄄城县第十二中学', '0', '237hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371726', '鄄城一中', '0', '238hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371726', '鄄城二中', '0', '239hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371726', '鄄城实验中学', '0', '240hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371726', '鄄城县旧城镇葛庄中学', '0', '241hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371725', '郓城县潘渡镇初级中学', '0', '242hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371725', '郓城县潘渡镇王井初级中学', '0', '243hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371725', '郓城县武安镇初级中学', '0', '244hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371725', '郓城县开发区初级中学', '0', '245hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371725', '郓城县郓城镇南城初级中学', '0', '246hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371725', '郓城县双桥乡初级中学', '0', '247hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371725', '郓城县黄安镇初级中学', '0', '248hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371725', '郓城县黄安镇徐垓初级中学', '0', '249hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371725', '郓城县唐庙乡三屯初级中学', '0', '250hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371725', '郓城县唐庙乡初级中学', '0', '251hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371725', '郓城县随官屯镇初级中学', '0', '252hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371725', '郓城县随官屯镇马尹庄初级中学', '0', '253hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371725', '郓城县张营镇初级中学', '0', '254hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371725', '郓城县张营镇大人初级中学', '0', '255hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371725', '郓城县杨庄集镇初级中学', '0', '256hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371725', '郓城县杨庄集镇常庄初级中学', '0', '257hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371725', '郓城县程屯镇初级中学', '0', '258hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371725', '郓城县程屯镇肖皮口初级中学', '0', '259hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371725', '郓城县侯咽集镇初级中学', '0', '260hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371725', '郓城县侯咽集镇梳洗楼初级中学', '0', '261hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371725', '郓城县李集乡初级中学', '0', '262hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371725', '郓城县黄集乡初级中学', '0', '263hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371725', '郓城县玉皇庙镇初级中学', '0', '264hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371725', '郓城县玉皇庙镇刘口初级中学', '0', '265hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371725', '郓城县张鲁集乡初级中学', '0', '266hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371725', '郓城县黄堆乡初级中学', '0', '267hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371725', '郓城县郭屯镇初级中学', '0', '268hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371725', '郓城县丁里长镇初级中学', '0', '269hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371725', '郓城县南赵楼乡初级中学', '0', '270hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371725', '郓城县水堡乡初级中学', '0', '271hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371725', '郓城县第一初级中学', '0', '272hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371725', '郓城县高级中学', '0', '273hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371725', '郓城县实验中学', '0', '274hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371725', '郓城一中', '0', '275hz');
INSERT INTO `u_school` (`dist_cd`, `school_name`, `school_type`, `prefix_code`) VALUES ('371725', '郓城县英华初级中学', '0', '276hz');

# tanghao
DROP TABLE IF EXISTS `r_book_lexile`;
CREATE TABLE `r_book_lexile` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `Lexilelevel` VARCHAR(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'Lexile等级',
  `minLexile` INT(255) DEFAULT NULL COMMENT '最小值',
  `maxLexile` INT(255) DEFAULT NULL COMMENT '最大值',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
INSERT INTO `langying`.`r_book_lexile` (`id`, `Lexilelevel`, `minLexile`, `maxLexile`) VALUES ('1', '1年级', '-500', '-400');
INSERT INTO `langying`.`r_book_lexile` (`id`, `Lexilelevel`, `minLexile`, `maxLexile`) VALUES ('2', '1年级~2年级', '-399', '-300');
INSERT INTO `langying`.`r_book_lexile` (`id`, `Lexilelevel`, `minLexile`, `maxLexile`) VALUES ('3', '2年级', '-299', '-151');
INSERT INTO `langying`.`r_book_lexile` (`id`, `Lexilelevel`, `minLexile`, `maxLexile`) VALUES ('4', '2年级~3年级\r\n', '-150', '-150');
INSERT INTO `langying`.`r_book_lexile` (`id`, `Lexilelevel`, `minLexile`, `maxLexile`) VALUES ('5', '3年级', '-149', '49');
INSERT INTO `langying`.`r_book_lexile` (`id`, `Lexilelevel`, `minLexile`, `maxLexile`) VALUES ('6', '3年级~4年级', '50', '99');
INSERT INTO `langying`.`r_book_lexile` (`id`, `Lexilelevel`, `minLexile`, `maxLexile`) VALUES ('7', '4年级~5年级', '100', '149');
INSERT INTO `langying`.`r_book_lexile` (`id`, `Lexilelevel`, `minLexile`, `maxLexile`) VALUES ('8', '4年级~预备年级', '150', '199');
INSERT INTO `langying`.`r_book_lexile` (`id`, `Lexilelevel`, `minLexile`, `maxLexile`) VALUES ('9', '4年级~初一年级', '200', '299');
INSERT INTO `langying`.`r_book_lexile` (`id`, `Lexilelevel`, `minLexile`, `maxLexile`) VALUES ('10', '5年级~初二年级', '300', '349');
INSERT INTO `langying`.`r_book_lexile` (`id`, `Lexilelevel`, `minLexile`, `maxLexile`) VALUES ('11', '预备年级~初二年级', '350', '399');
INSERT INTO `langying`.`r_book_lexile` (`id`, `Lexilelevel`, `minLexile`, `maxLexile`) VALUES ('12', '初一年级~初三年级', '400', '449');
INSERT INTO `langying`.`r_book_lexile` (`id`, `Lexilelevel`, `minLexile`, `maxLexile`) VALUES ('13', '初二年级~初三年级', '450', '499');
INSERT INTO `langying`.`r_book_lexile` (`id`, `Lexilelevel`, `minLexile`, `maxLexile`) VALUES ('14', '初二年级~高一年级', '500', '549');
INSERT INTO `langying`.`r_book_lexile` (`id`, `Lexilelevel`, `minLexile`, `maxLexile`) VALUES ('15', '初三年级~高一年级', '550', '649');
INSERT INTO `langying`.`r_book_lexile` (`id`, `Lexilelevel`, `minLexile`, `maxLexile`) VALUES ('16', '高一年级~高二年级', '650', '699');
INSERT INTO `langying`.`r_book_lexile` (`id`, `Lexilelevel`, `minLexile`, `maxLexile`) VALUES ('17', '高一年级~高三年级', '700', '749');
INSERT INTO `langying`.`r_book_lexile` (`id`, `Lexilelevel`, `minLexile`, `maxLexile`) VALUES ('18', '高二年级~高三年级', '750', '799');
INSERT INTO `langying`.`r_book_lexile` (`id`, `Lexilelevel`, `minLexile`, `maxLexile`) VALUES ('19', '高三年级', '800', '2000');

# liwei

# 产品选择页
ALTER TABLE u_user_product_history MODIFY user_id INT(11) NOT NULL COMMENT '用户 id';
ALTER TABLE u_user_product_history MODIFY product_id CHAR(2) NOT NULL COMMENT '用户进入的产品：1、学校版写作；2、阅读奇妙旅；3、背单词；4、个人版写作；5、新平台进入旧平台产品选择页；6、新平台进入新阅读系统';
ALTER TABLE u_user_product_history MODIFY login_date CHAR(19) NOT NULL COMMENT '登录时间';

# 增加字段：
ALTER TABLE u_user_product_history ADD user_agent VARCHAR(400) DEFAULT NULL COMMENT "http 请求头中的 user-agent 信息";

# 读物评价常量表
DROP TABLE IF EXISTS c_article_evaluate_label;
CREATE TABLE c_article_evaluate_label(
   id                   INT,
   label_content        VARCHAR(10) COLLATE utf8_unicode_ci NOT NULL COMMENT '标签内容',
   create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP  COMMENT '创建时间',
   update_time TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '每次更新时，刷新该字段值',
   is_active            CHAR(1) COMMENT '是否有效：1有效，0无效'
);
ALTER TABLE c_article_evaluate_label COMMENT '读物评价常量表';

# 常量数据
INSERT INTO c_article_evaluate_label(id,label_content,is_active) VALUES('1','图片好看','1');
INSERT INTO c_article_evaluate_label(id,label_content,is_active) VALUES('2','非常有用','1');
INSERT INTO c_article_evaluate_label(id,label_content,is_active) VALUES('3','故事有趣','1');
INSERT INTO c_article_evaluate_label(id,label_content,is_active) VALUES('4','有点难度','1');
INSERT INTO c_article_evaluate_label(id,label_content,is_active) VALUES('5','相当轻松','1');

# 阅读日志表
DROP TABLE IF EXISTS r_reading_log;
CREATE TABLE `r_reading_log` (
  `reading_log_id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_id` INT(11) DEFAULT NULL COMMENT '应该设置为外键，关联外部数据库 u_user表的主键',
  `book_id` VARCHAR(50) COLLATE utf8_bin DEFAULT NULL COMMENT '应该设置为外键，关联外部数据库 t_article_info表的主键',
  `is_finish` CHAR(1) COLLATE utf8_bin DEFAULT '0' COMMENT '是否读完：1读完，0未读完',
  `evaluate_id` VARCHAR(15) COLLATE utf8_bin DEFAULT NULL COMMENT '用户对购买书籍的评价，应设置为外键，如果考虑评价可以多项选择，可以使用数组',
  `create_time` CHAR(19) COLLATE utf8_bin DEFAULT NULL COMMENT '创建时间',
  `update_time` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '每次更新时，刷新该字段值',
  `is_active` CHAR(1) COLLATE utf8_bin DEFAULT NULL COMMENT '是否有效：1有效，0无效',
  PRIMARY KEY (`reading_log_id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='阅读日志表';

# 用户已经购买的书籍列表
# r_user_book 这张表用于记录用户买了哪些书
DROP TABLE IF EXISTS r_user_book;
CREATE TABLE `r_user_book` (
  `user_book_id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_id` INT(11) NOT NULL COMMENT '应该设置为外键，关联外部数据库 u_user表的主键',
  `book_id` VARCHAR(50) COLLATE utf8_bin NOT NULL COMMENT '应该设置为外键，关联外部数据库 t_article_info表的主键',
  `create_time` CHAR(19) COLLATE utf8_bin DEFAULT NULL COMMENT '创建时间',
  `update_time` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '每次更新时，刷新该字段值',
  `read_status` CHAR(1) COLLATE utf8_bin NOT NULL COMMENT '0：正在阅读，1：阅读完成 , 2: 未开始阅读',
  `is_active` CHAR(1) COLLATE utf8_bin DEFAULT NULL COMMENT '是否有效：1有效，0无效',
  PRIMARY KEY (`user_book_id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户已经购买的书籍列表';

# r_user_operate_log 记录用户阅读行为数据日志的
DROP TABLE IF EXISTS `r_user_operate_log`;
CREATE TABLE `r_user_operate_log` (
  `id` INT(16) NOT NULL AUTO_INCREMENT,
  `user_id` INT(16) NOT NULL,
  `book_id` VARCHAR(40) COLLATE utf8_bin NOT NULL,
  `operate` INT(10) NOT NULL COMMENT '用户操作: 1.上一页  2.下一页  3.退出  4.点击音频',
  `create_time` VARCHAR(20) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
ALTER TABLE `r_user_operate_log` COMMENT '记录用户阅读行为数据日志';
# 20160329 end

 ALTER TABLE `r_user_book` ADD price INT(1) NOT NULL COMMENT '书本单价';


 ############################ 20160407 ########################
 DROP TABLE IF EXISTS r_user_gold;
CREATE TABLE r_user_gold(
user_gold_id INT(11) PRIMARY KEY AUTO_INCREMENT COMMENT '用户金币表主键',
user_id INT(11) NOT NULL UNIQUE COMMENT '用户 id',
gold_num INT(11) NOT NULL DEFAULT 0 COMMENT '用户金币数量',
create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP  COMMENT '创建时间',
update_time TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '每次更新时，刷新该字段值',
is_active CHAR(1) COMMENT '是否有效：1有效，0无效',
FOREIGN KEY(user_id) REFERENCES u_user(user_id)
);
ALTER TABLE r_user_gold COMMENT '新阅读用户拥有金币数量表';

# 以下是测试数据
INSERT INTO r_user_gold VALUES(NULL,602239,50,NULL,NULL,'1');

# 为 user_id 建立索引
create index gold_user_id_idex on r_user_gold(user_id);
#DROP TABLE IF EXISTS r_user_gold_detail;
CREATE TABLE r_user_gold_detail(
user_gold_detail_id INT(11) PRIMARY KEY AUTO_INCREMENT COMMENT '用户金币明细表主键',
user_id INT(11) NOT NULL COMMENT '用户 id',
gold_change INT(11) NOT NULL DEFAULT 0 COMMENT '用户金币变化，正数表示增加，负数表示减少',
detail_type CHAR(1) COMMENT '0初始化（由系统赠送）,1用户充值增加，2用户购买读物消耗',
create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP  COMMENT '创建时间',
is_active CHAR(1) COMMENT '是否有效：1有效，0无效',
FOREIGN KEY(user_id) REFERENCES u_user(user_id)
);
ALTER TABLE r_user_gold_detail COMMENT '新阅读用户拥有金币数量明细表';



#########################################  20160407 #######################
DROP TABLE IF EXISTS t_user_order;
CREATE TABLE IF NOT EXISTS t_user_order(
  order_id INT(11) PRIMARY KEY AUTO_INCREMENT COMMENT '订单表主键',
  order_type CHAR(1) NOT NULL COMMENT '1：支付宝，2：微信',
  trade_no VARCHAR(30) NOT NULL UNIQUE COMMENT '订单编号（规则内部自定义）',
  user_id INT(11) NOT NULL COMMENT '用户 id',
  `status` CHAR(1) NOT NULL DEFAULT '0' COMMENT '0：交易创建，等待卖家付款，1：交易关闭，2：交易成功，3：等待卖家收款（如果卖家账号被冻结），4：交易成功且结束',
  total_fee FLOAT(6,2) NOT NULL COMMENT '下单金额',
  `subject` VARCHAR(30) NOT NULL COMMENT '订单名称',
  order_info VARCHAR(1000) COMMENT '其它第三方交易平台返回的信息',
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '下单时间',
  update_time TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间（接收异步通知的时间）',
  FOREIGN KEY(user_id) REFERENCES u_user(user_id)
);

ALTER TABLE t_user_order COLLATE=utf8_bin COMMENT '用户订单表';

# 为订单表添加普通索引
ALTER TABLE t_user_order ADD INDEX index_order_user_id(user_id);
ALTER TABLE t_user_order COMMENT'朗鹰用户订单表';


 ALTER TABLE `r_user_gold_detail` ADD basic_gold INT(11) NOT NULL COMMENT '消耗或增长金币前的金币数量';
 ALTER TABLE `r_user_gold_detail` ADD gold_order_id INT(11)  COMMENT '如果是购买金币填此信息';


 ALTER TABLE `t_user_order` ADD gold_num INT(11)  COMMENT '金币数量';

 ALTER TABLE r_user_gold_detail ADD book_id VARCHAR(40) COMMENT '消耗金币对应的购买书本的 id';


 # 20160504
 CREATE TABLE `r_user_doquestion` (
  `user_doquestion_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '应该设置为外键，关联外部数据库 u_user表的主键',
  `user_book_id` int(11) DEFAULT NULL COMMENT '应该设置为外键，关联外部数据库 r_user_book表的主键',
  `book_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '应该设置为外键，关联外部数据库 t_article_info表的主键',
  `question_id` varchar(50) NOT NULL COMMENT '应该设置为外键，关联外部数据库 t_question_info表的主键',
  `sort` int(11) DEFAULT NULL COMMENT '题号',
  `user_dotimes` int(11) NOT NULL COMMENT '做题次数',
  `create_time` char(19) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '每次更新时，刷新该字段值',
  `do_status` char(1) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '0：未做，1：已做',
  `is_active` char(1) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '是否有效：1有效（当前正在做题记录），0无效（历史做题记录）',
  PRIMARY KEY (`user_doquestion_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `r_user_question_option` (
  `user_doquestion_option_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '应该设置为外键，关联外部数据库 u_user表的主键',
  `user_doquestion_id` int(11) DEFAULT NULL COMMENT '应该设置为外键，关联外部数据库 r_user_doquestion表的主键',
  `user_option_id` varchar(50) DEFAULT NULL COMMENT '用户选择的答案。应该设置为外键，关联外部数据库 t_question_option表的主键',
  `isright` varchar(1) DEFAULT NULL COMMENT '是否正确1：正确，0错误',
  `create_time` char(19) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '每次更新时，刷新该字段值',
  `is_active` char(1) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '是否有效：1有效，0无效',
  PRIMARY KEY (`user_doquestion_option_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

################################ 2016 年 5 月 9 日##############################################################

ALTER TABLE u_user ADD user_pwd CHAR(32) COMMENT '密码（密文）';
ALTER TABLE u_user ADD `encrypt` CHAR(1) DEFAULT 0 COMMENT '明文密码是否经过加密处理：0 未加密，1 加密';


UPDATE u_user SET user_pwd=MD5(CONCAT('L',user_password,'οY')), ENCRYPT=1 WHERE user_name NOT LIKE 'lyο%';
UPDATE u_user SET user_pwd=user_password ,ENCRYPT=1 WHERE user_name LIKE 'lyο%';


################################ 2016 年 5 月 16 日##############################################################

CREATE TABLE r_grade_book(
id INT(1) PRIMARY KEY AUTO_INCREMENT COMMENT '年级和书本对应表主键',
grade_label VARCHAR(5) COMMENT '年级标签',
grade_name VARCHAR(10) COMMENT '年级名',
min_lexile INT(1) COMMENT '最小值',
max_lexile INT(1) COMMENT '最大值'
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
ALTER TABLE r_grade_book COMMENT '新阅读年级和书本蓝思值对应表';

# 插入数据
INSERT INTO r_grade_book(grade_label,grade_name,min_lexile,max_lexile)
VALUES('P1','一年级',-500,-300),
('P2','二年级',-350,-150),
('P3','三年级',-150,50),
('P4','四年级',50,250),
('P5','五年级',100,300),
('P6','六年级（预初）',150,350),
('M1','初一',200,400),
('M2','初二',300,500),
('M3','初三',400,600),
('H1','高一',500,700),
('H2','高二',600,800),
('H3','高三',700,1500);


# 2016 年 5 月 23 日
# 自由注册用户对应年级表
# 应该设置联合唯一索引
DROP TABLE IF EXISTS u_register_grade;
CREATE TABLE u_register_grade(
  id INT PRIMARY KEY AUTO_INCREMENT COMMENT '自由注册用户对应年级 id',
  user_id INT COMMENT '用户 id',
  grade_id CHAR(2) COMMENT '年级 id',
  FOREIGN KEY(user_id) REFERENCES u_user(user_id),
  FOREIGN KEY(grade_id) REFERENCES u_grade (grade_id),
  UNIQUE KEY user_grade_unique (user_id,grade_id)
);

ALTER TABLE u_user ADD user_type CHAR(1) NOT NULL DEFAULT 1 COMMENT '用户类型：1教学用户，2活动用户';

# 2016 年 6 月 22 日
删除触发器
drop trigger u_user_update;
删除字段
ALTER TABLE u_user DROP user_password;
ALTER TABLE u_user DROP encrypt;

ALTER TABLE langying.t_user_order MODIFY COLUMN order_info VARCHAR(1000)
CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '其它第三方交易平台返回的信息';


项目有：
（1）学校版写作（lyced 验证完毕）
（2）学校版阅读（lyced 验证完毕）
（3）LY（lyced 验证完毕）
（4）阅读跟读（没有密码相关）
（5）langyingapi （lyced 验证完毕）
（6）快速写作
（7）背单词（lyced 验证完毕）


