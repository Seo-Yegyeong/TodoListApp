package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        System.out.println();
        System.out.println("----------------Menu---------------");
        System.out.println("1. Add a new item ( add )");
        System.out.println("2. Delete an existing item ( del )");
        System.out.println("3. Update an item  ( edit )");
        System.out.println("4. List all items ( ls )");
        System.out.println("5. sort the list by name ( ls_name_asc )");
        System.out.println("6. sort the list by name ( ls_name_desc )");
        System.out.println("7. sort the list by date ( ls_date )");
        System.out.println("8. sort the list by date with reverse order ( ls_date_desc )");
        System.out.println("9. find items with id number ( find_item_id )");
        System.out.println("10. find items with category name ( find_item_cate )");
        System.out.println("11. find items which include some words ( find_item )");
        System.out.println("12. show all kinds of category ( ls_cate )");
        System.out.println("13. mark an item as completed ( comp )");
        System.out.println("14. print completed item list ( ls_comp )");
        System.out.println("15. print not completed item list ( ls_not_comp )");
        System.out.println("16. print menu list ( help )");
        System.out.println("17. exit ( exit )");
        System.out.println("------------------------------------");
    }
    
    public static void prompt() {
    	System.out.print("메뉴 목록 중 괄호 안의 단어를 입력해주세요. (도움 필요 시 help 입력) > ");
    }
}
