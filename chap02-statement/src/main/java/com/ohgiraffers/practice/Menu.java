package com.ohgiraffers.practice;

import com.ohgiraffers.model.dto.EmployeeDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public static class Menu {
    EmployeeDTO deleteMenu = new EmployeeDTO();

    Connection con = getConnection();
    PreparedStatement pstmt = null;
    Properties prop = new Properties();
    ResultSet rset = null;
    EmployeeDTO emp = null;
    List<EmployeeDTO> empList = null;

    Scanner sc = new Scanner(System.in);
    int select = 0;

    while(select!=9){
        System.out.println("검색할 메뉴를 선택해 주세요");
        System.out.println("1.이름으로 검색");
        System.out.println("2.부서코드로 검색");
        System.out.println("3.급여범위로 검색");
        System.out.println("4.보너스여부 검색");
        System.out.println("5.전화번호로 검색");
        System.out.println("6.직급과 급여로 검색");
        System.out.println("9.검색 종료");
        select = sc.nextInt();
        sc.nextLine();

        switch (select) {
            case 1:
                System.out.println("이름 전체 또는 포함된 글자를 입력해 주세요");
                String name = sc.nextLine();
                searchbyName(name);
                break;

            case 2:
                System.out.println("부서코드를 입력해 주세요");
                String dCode = sc.nextLine();
                searchbyDcode(dCode);
                break;

            case 3:
                System.out.println("급여범위를 A 이상 B 이하(최대1000만) 순서로 입력해 주세요 ");
                System.out.print("A : ");
                int a = sc.nextInt();
                System.out.print("B : ");
                int b = sc.nextInt();
                sc.nextLine();
                searchbySalary(a, b);
                break;

            case 4:
                System.out.println("보너스를 받는 사람을 알고싶으면 Y, 아니면 N 을 입력해 주세요");
                String YN = sc.nextLine();
                bonusCheck(YN);
                break;

            case 5:
                System.out.println("A번째 자리에 B숫자가 들어있는 직원을 검색합니다.");
                System.out.print("A : ");
                a = sc.nextInt();
                System.out.print("B : ");
                String bb = sc.nextLine();
                searchbyTelephon(a, bb);
                break;

            case 6:
                System.out.println("직급과 급여범위로 직원을 검색합니다.");
                System.out.println("직급 A, 직급 B, 급여 A이상, 급여 B이하(최대 1000만)");
                String aa = sc.nextLine();
                bb = sc.nextLine();
                a = sc.nextInt();
                b = sc.nextInt();
                sc.nextLine();
                searchbyJcodewhithSalary(aa, bb, a, b);
                break;

            case 9:
                System.out.println("프로그램을 종료합니다.");
                break;

            default:
                System.out.println("다른번호를 입력해 주세요");

        }
    }

    private void searchbyName(String name) {
        try {
            prop.loadFromXML(
                    new FileInputStream("src/main/java/com/ohgiraffers/mapper/employee-query.xml")
            );
            String query = prop.getProperty("searchbyName");

            pstmt=con.prepareStatement(query);
            pstmt.setString(1,name);

            result=pstmt.executeUpdate();
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(pstmt);
            close(con);
        }

        try {
            pstmt = con.createStatement();
            rset = pstmt.executeQuery(query);
            empList = new ArrayList<>();

            while(rset.next()){
                emp = new EmployeeDTO();
                emp.setEmpId(rset.getString("EMP_ID"));
                emp.setEmpName(rset.getString("EMP_NAME"));
                emp.setEmpNo(rset.getString("EMP_NO"));
                emp.setEmail(rset.getString("EMAIL"));
                emp.setPhone(rset.getString("PHONE"));
                emp.setDeptCode(rset.getString("DEPT_CODE"));
                emp.setJobCode(rset.getString("JOB_CODE"));
                emp.setSalary(rset.getInt("SALARY"));
                emp.setBonus(rset.getDouble("BONUS"));
                emp.setManagerId(rset.getString("MANAGER_ID"));
                emp.setHireDate(rset.getDate("HIRE_DATE"));
                emp.setEntDate(rset.getDate("ENT_DATE"));
                emp.setEntYn(rset.getString("ENT_YN"));

                empList.add(emp);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(rset);
            close(stmt);
            close(con);
        }

        for(EmployeeDTO e : empList){
            System.out.println("e = " + e);
        }




    }







        try {
        prop.loadFromXML(
                new FileInputStream("src/main/java/com/ohgiraffers/mapper/employee-query.xml")
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


