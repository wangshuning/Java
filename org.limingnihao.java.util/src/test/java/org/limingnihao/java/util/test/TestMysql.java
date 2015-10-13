package org.limingnihao.java.util.test;

import org.junit.Test;
import org.limingnihao.util.DateUtil;
import org.limingnihao.util.MySqlUtil;

/**
 *
 */
public class TestMysql {

    @Test
    public void save(){
        MySqlUtil util = new MySqlUtil();
        System.out.println("start - " + DateUtil.getNowString());
        for(int i=0; i<10; i++){
            util.execute("insert into uid_info(uid, id_type, id_value) value(uuid(), '1', '2');");
            util.execute("insert into uid_info(uid, id_type, id_value) value(uuid(), '我的', '2');");

        }
        System.out.println("over - " + DateUtil.getNowString());

    }
}
