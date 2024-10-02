package com.ohgiraffers.section02.preparedstatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Application1 {
    public static void main(String[] args) {
        // preparedstatment는 쿼리문을 매번 파싱하지 않고 이전 쿼리를 캐쉬해놓고 찾아서 씀
        Connection con = getConnection();
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        String query = " SELECT EMP_ID, EMP_NAME FROM EMPLOYEE";

        try {
            pstmt = con.prepareStatement(query);
            rset = pstmt.executeQuery();

            while(rset.next()) {
                System.out.println(rset.getString("EMP_ID")
                        + rset.getString("EMP_NAME"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rset);
            close(con);
            close(pstmt);
        }
    }
}
