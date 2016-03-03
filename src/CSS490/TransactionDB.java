package CSS490;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class TransactionDB extends Database {

	public static boolean create(Transaction trans) {
		PreparedStatement stmtTran = null;
		PreparedStatement stmtLog = null;
		PreparedStatement stmtSel = null;
		ResultSet rs = null;
		
		boolean result = false;
		try{
			if (connect()) {
				String insTran = "insert into transaction_list values (0, ?, ?, ?)";
				String insLog = "insert into transaction_log values (?, ?, ?, ?, ?)";
				String select = "select * from transaction_list where customer_id = ? AND _date = ?";
				
				Date dt = new java.util.Date();
				SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String currentTime = sdf.format(dt);
				
				stmtTran = conn.prepareStatement(insTran);
				stmtLog = conn.prepareStatement(insLog);
				stmtSel = conn.prepareStatement(select);
				
				stmtTran.setInt(1, trans.getUserId());
				stmtTran.setString(2, currentTime);
				stmtTran.setDouble(3, trans.getTotal());
				stmtTran.executeUpdate();
				
				int trans_id = -1;
				stmtSel.setInt(1, trans.getUserId());
				stmtSel.setString(2, currentTime);
				rs = stmtSel.executeQuery();
				while (rs.next()) {
					trans_id = rs.getInt("transaction_id");
				}
				
				Cart cart = trans.getCart();
				ArrayList<CartItem> items = cart.getCart();
				
				for (CartItem i:items) {
					double price = i.getBook().getPrice();
					int quantity = i.getQuantity();
					stmtLog.setInt(1, trans_id);
					stmtLog.setInt(2, i.getBook().getProductId());
					stmtLog.setDouble(3, price);
					stmtLog.setInt(4, quantity);
					stmtLog.setDouble(5, price * quantity);
					stmtLog.executeUpdate();
				}
				result = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmtTran, conn, rs);
			closeAll(stmtLog, null);
			closeAll(stmtSel, null);
		}
		return result;
	}
}
