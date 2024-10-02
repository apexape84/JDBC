package com.ohgiraffers.section02.update;

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

public class Application1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("변경할 메뉴의 번호를 입력해 주세요 : ");
        int menucode = sc.nextInt();
        sc.nextLine();
        System.out.print("메뉴의 이름을 무엇으로 변경할까요? : ");
        String menuName = sc.nextLine();
        System.out.print("메뉴의 가격을 얼마로 변경할까요? : ");
        int menuPrice = sc.nextInt();
        sc.nextLine();

        MenuDTO changeMenu = new MenuDTO();
        changeMenu.setMenuCode(menucode);
        changeMenu.setMenuName(menuName);
        changeMenu.setMenuPrice(menuPrice);

        Connection con = getConnection();
        PreparedStatement pstmt = null;
        Properties prop = new Properties();
        int result = 0;

        try {
            prop.loadFromXML(
                    new FileInputStream("src/main/java/com/ohgiraffers/mapper/menu-query.xml")
            );
            String query = prop.getProperty("updateMenu");

            pstmt=con.prepareStatement(query);
            pstmt.setInt(3,changeMenu.getMenuCode());
            pstmt.setString(1,changeMenu.getMenuName());
            pstmt.setInt(2,changeMenu.getMenuPrice());


            result=pstmt.executeUpdate();
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(pstmt);
            close(con);
        }
        if(result!=0)
            System.out.println("메뉴수정 성공!");
        else
            System.out.println("알 수 없는 이유로 메뉴등록 실패");
    }
}
