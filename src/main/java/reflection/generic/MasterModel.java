package reflection.generic;

import reflection.ConnectionHelper;
import reflection.Customers;
import reflection.myannotation.Table;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MasterModel<M> {
    public void save (M obj){
       try{
           Class clazz = obj.getClass();// Lấy thông tin lớp 'Class' từ đối tượng
           //1. kết nối đến database
           Connection cnn = ConnectionHelper.getConnection();
           Statement stt = cnn.createStatement();
           String tableName = clazz.getSimpleName();
           Table table = (Table) clazz.getAnnotation(Table.class);
           if (table.name() != null && !table.name().isEmpty()){
               tableName = table.name();
           }
           StringBuilder fieldNamesBuilder = new StringBuilder();
           fieldNamesBuilder.append("(");
           Field[] fields = clazz.getDeclaredFields();
           for (Field value : fields) {
               fieldNamesBuilder.append(value.getName());
               fieldNamesBuilder.append(", ");
           }
           fieldNamesBuilder.setLength(fieldNamesBuilder.length() - 2);
           fieldNamesBuilder.append(")");
           StringBuilder fieldValuesBuilder = new StringBuilder();
           fieldValuesBuilder.append("(");
           for (Field field : fields) {
               field.setAccessible(true);
               if (field.getType().getSimpleName().equals("String")) {
                   fieldValuesBuilder.append("'");
                   fieldValuesBuilder.append(field.get(obj));
                   fieldValuesBuilder.append("', ");
               } else {
                   fieldValuesBuilder.append(field.get(obj));
                   fieldValuesBuilder.append(", ");
               }
           }
           fieldValuesBuilder.setLength(fieldValuesBuilder.length() - 2);
           fieldValuesBuilder.append(")");
//        //2. Tạo ra câu lệnh truy vấn.
           String sqlQuery = String.format("insert into %s " +
                   "%s " +
                   "values %s", tableName, fieldNamesBuilder.toString(), fieldValuesBuilder.toString());
           System.out.println(sqlQuery);
//        //3.Gửi câu lênh vào database.
           stt.execute(sqlQuery);
       }catch (Exception exp){
           System.err.println("System error!!!");
       }
    }
    public void delete (M obj){
        try{
            Class clazz = obj.getClass();// Lấy thông tin lớp 'Class' từ đối tượng
            //1. kết nối đến database
            Connection cnn = ConnectionHelper.getConnection();
            Statement stt = cnn.createStatement();
            String tableName = clazz.getSimpleName();
            Table table = (Table) clazz.getAnnotation(Table.class);
            if (table.name() != null && !table.name().isEmpty()){
                tableName = table.name();
            }
            StringBuilder fieldNamesBuilder = new StringBuilder();
            fieldNamesBuilder.append("(");
            Field[] fields = clazz.getDeclaredFields();
            for (Field value : fields) {
                fieldNamesBuilder.append(value.getName());
                fieldNamesBuilder.append(", ");
            }
            fieldNamesBuilder.setLength(fieldNamesBuilder.length() - 2);
            fieldNamesBuilder.append(")");
//        //2. Tạo ra câu lệnh truy vấn.
            String sqlQuery = String.format("delete from %s ", tableName);
            System.out.println(sqlQuery);
//        //3.Gửi câu lênh vào database.
            stt.execute(sqlQuery);
        }catch (Exception exp){
            System.err.println("System error!!!");
        }

    }
    public void update (M obj){
        try{
            Class clazz = obj.getClass(); // Lấy thông tin lớp 'Class' từ đối tượng
            //1. kết nối đến database
            Connection cnn = ConnectionHelper.getConnection();
            Statement stt = cnn.createStatement();
            String tableName = clazz.getSimpleName();
            Table table = (Table) clazz.getAnnotation(Table.class);
            if (table.name() != null && !table.name().isEmpty()){
                tableName = table.name();
            }
            StringBuilder fieldNamesBuilder = new StringBuilder();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.getType().getSimpleName().equals("String")) {
                    fieldNamesBuilder.append(field.getName());
                    fieldNamesBuilder.append(" = ");
                    fieldNamesBuilder.append("'");
                    fieldNamesBuilder.append(field.get(obj));
                    fieldNamesBuilder.append("', ");
                } else {
                    fieldNamesBuilder.append(field.getName());
                    fieldNamesBuilder.append(" = ");
                    fieldNamesBuilder.append(field.get(obj));
                    fieldNamesBuilder.append(", ");
                }
                fieldNamesBuilder.append(" ");
            }
            fieldNamesBuilder.setLength(fieldNamesBuilder.length() - 3);

//        //2. Tạo ra câu lệnh truy vấn.
            String sqlQuery = String.format("update  %s " +
                    "set %s " +
                    "where 1 ", tableName, fieldNamesBuilder.toString());
            System.out.println(sqlQuery);
//        //3.Gửi câu lênh vào database.
            stt.execute(sqlQuery);
        }catch (Exception exp){
            System.err.println("System error!!!");
        }
    }
    public List<M> findAll(){
        ArrayList<M> list = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).toString());
        }
        return  list;
    }
}
