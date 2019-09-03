package jdbc;
import java.sql.*;
public class getMetaData {
	PreparedStatement ps=null;
	ResultSet rs=null;
    public static void main(String[] args) {
        
        String driverClassName = "oracle.jdbc.OracleDriver";
        String url = "jdbc:oracle:thin:@127.0.0.1:1521:tengd";
        String password = "yyp";
        String user= "yyp";
        
        try {
            Class.forName(driverClassName);
        } catch (ClassNotFoundException ex) {
            System.out.println("加载错误！");
        }
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("连接成功");
            
            String sql = "select * from u_user u where u.u_name='admin'";
            PreparedStatement ps=conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=ps.executeQuery();
            ResultSetMetaData md = ps.getMetaData();  //ps.getMetaData();//
            for (int i = 1; i <= md.getColumnCount(); i++) {
                System.out.println("name:"+md.getColumnName(i)+"   label:"+md.getColumnLabel(i));
            }
           /* sql = "select * from u_user u  where 1=1 and u.u_name='admin'";
            ps=conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rs=ps.executeQuery();
            md = rs.getMetaData();  //ps.getMetaData();//
            for (int i = 1; i <= md.getColumnCount(); i++) {
                System.out.println("name:"+md.getColumnName(i)+"   label:"+md.getColumnLabel(i));
            }*/
            
        } catch (SQLException ex1) {
            System.out.println(ex1);
            System.out.println("失败");
        }finally{
        	if(conn!=null){
        		try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        }
    }
}