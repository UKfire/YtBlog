package pl.surecase.eu;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyDaoGenerator {

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(1, "greendao");
        addUser(schema);
        new DaoGenerator().generateAll(schema, args[0]);
    }

    private static void addUser(Schema schema) {
        Entity user = schema.addEntity("User");
        user.addStringProperty("funId").primaryKey();
        user.addStringProperty("email");
        user.addStringProperty("nickname");
        user.addStringProperty("createTime");
        user.addIntProperty("designer");
        user.addStringProperty("sex");
        user.addStringProperty("phoneNumber");
        user.addStringProperty("qqNumber");
        user.addStringProperty("motto");
        user.addStringProperty("province");
        user.addStringProperty("city");
        user.addStringProperty("headImage");
        user.addStringProperty("backImage");
    }


}
