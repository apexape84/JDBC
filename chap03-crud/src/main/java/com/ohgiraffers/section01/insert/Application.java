package com.ohgiraffers.section01.insert;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Application {
    public static void main(String[] args) {
        Connection con = getConnection();
        PreparedStatement pstmt = null;
        int result = 0;

        Properties prop = new Properties();

        try {
            prop.loadFromXML(
                    new FileInputStream("src/main/java/com/ohgiraffers/mapper/menu-query.xml")
            );
            String query = prop.getProperty("insertMenu");
            System.out.println("query = " + query);

            Scanner sc = new Scanner(System.in);
            System.out.print("신규메뉴의 이름을 입력해 주세요 : ");
            String menuName = sc.nextLine();
            System.out.print("신규메뉴의 이름을 입력해 주세요 : ");
            int menuPrice = sc.nextInt();
            System.out.print("신규메뉴의 카테고리코드를 입력해 주세요 : ");
            int categoryCode = sc.nextInt();
            sc.nextLine();
            System.out.print("신규메뉴의 판매여부를 입력해 주세요(Y/N) : ");
            String orderableStatus = sc.nextLine();

            pstmt=con.prepareStatement(query);
            pstmt.setString(1,menuName);
            pstmt.setInt(2,menuPrice);
            pstmt.setInt(3,categoryCode);
            pstmt.setString(4,orderableStatus);

            result=pstmt.executeUpdate();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(pstmt);
            close(con);
        }

        System.out.println("result = " + result);

        if(result!=0)
            System.out.println("메뉴등록 성공!");
        else
            System.out.println("알 수 없는 이유로 메뉴등록 실패");

    }
}
