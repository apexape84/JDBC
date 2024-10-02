package com.ohgiraffers.section03.delete;

import com.ohgiraffers.model.dto.MenuDTO;

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
        Scanner sc = new Scanner(System.in);
        System.out.print("삭제할 메뉴의 번호를 입력해 주세요 : ");
        int menucode = sc.nextInt();
        sc.nextLine();

        MenuDTO deleteMenu = new MenuDTO();
        deleteMenu.setMenuCode(menucode);

        Connection con = getConnection();
        PreparedStatement pstmt = null;
        Properties prop = new Properties();
        int result = 0;

        try {
            prop.loadFromXML(
                    new FileInputStream("src/main/java/com/ohgiraffers/mapper/menu-query.xml")
            );
            String query = prop.getProperty("deleteMenu");

            pstmt=con.prepareStatement(query);
            pstmt.setInt(1,deleteMenu.getMenuCode());

            result=pstmt.executeUpdate();
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(pstmt);
            close(con);
        }
        if(result!=0)
            System.out.println("메뉴삭제 성공!");
        else
            System.out.println("알 수 없는 이유로 삭제 실패");
    }
}
