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
		PreparedStatement stmtUpd = null;
		PreparedStatement stmtInv = null;
		ResultSet rs = null;
		ResultSet rsb = null;
		
		boolean result = false;
		try{
			if (connect()) {
				String insTran = "insert into transaction_list values (0, ?, ?, ?)";
				String insLog = "insert into transaction_log values (?, ?, ?, ?, ?)";
				String select = "select * from transaction_list where customer_id = ? AND _date = ?";
				String bookInv = "select * from book_inventory where book_id = ?";
				String bookUpd = "update book_inventory set inven_amount = ? where book_id = ?";
				
				Date dt = new java.util.Date();
				SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String currentTime = sdf.format(dt);
				
				stmtTran = conn.prepareStatement(insTran);
				stmtLog = conn.prepareStatement(insLog);
				stmtSel = conn.prepareStatement(select);
				stmtUpd = conn.prepareStatement(bookUpd);
				stmtInv = conn.prepareStatement(bookInv);
				
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
					
					stmtInv.setInt(1, i.getBook().getProductId());
					rsb = stmtInv.executeQuery();
					int currQ = -1;
					while (rsb.next()) {
						currQ = rsb.getInt("inven_amount");
					}
					
					if (currQ >= quantity) {
						stmtLog.setInt(1, trans_id);
						stmtLog.setInt(2, i.getBook().getProductId());
						stmtLog.setDouble(3, price);
						stmtLog.setInt(4, quantity);
						stmtLog.setDouble(5, price * quantity);
						stmtLog.executeUpdate();
						
						int newQuantity = currQ - quantity;
						stmtUpd.setInt(1, newQuantity);
						stmtUpd.setInt(2, i.getBook().getProductId());
						stmtUpd.executeUpdate();
					}
				}
				result = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmtTran, conn, rs);
			closeAll(stmtLog, null);
			closeAll(stmtSel, null);
			closeAll(stmtUpd, null);
		}
		return result;
	}
}
