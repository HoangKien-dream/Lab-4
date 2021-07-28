package reflection.generic;

import reflection.ConnectionHelper;
import reflection.myannotation.Table;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Statement;

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

    }
    public void update (M obj){

    }
}
