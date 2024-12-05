package com.choongang;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

public class MyStorageTest {
    private MyStorage storage;

    @BeforeEach
    void setUp() {
        storage = new MyStorage();
        // 배열을 초기 상태로 설정
        for (int i = 0; i < MyStorage.products.length; i++) {
            MyStorage.products[i] = MyStorage.EMPTY;
            MyStorage.counts[i] = 0;
        }
    }

    @Test
    @DisplayName("제품 등록 메서드 테스트(prodInput)가 올바르게 제품을 추가해야 합니다.")
    void testProdInput() {
        String input = "TestProduct\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        storage.prod_input(scanner);

        assertEquals("TestProduct", MyStorage.products[0], "제품이 올바르게 등록되어야 합니다.");
    }

    @Test
    @DisplayName("제품 삭제 메서드 테스트(prodRemove)가 올바르게 제품을 제거해야 합니다.")
    void testProdRemove() {
        MyStorage.products[0] = "TestProduct";
        String input = "TestProduct\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        storage.prod_remove(scanner);

        assertEquals(MyStorage.EMPTY, MyStorage.products[0], "제품이 올바르게 제거되어야 합니다.");
    }

    @Test
    @DisplayName("제품 수량 증가 메서드 테스트(prodAmountAdd)가 올바르게 수량을 증가시켜야 합니다.")
    void testProdAmountAdd() {
        MyStorage.products[0] = "TestProduct";
        MyStorage.counts[0] = 5;
        String input = "TestProduct\n3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        storage.prod_amount_add(scanner);

        assertEquals(8, MyStorage.counts[0], "제품 수량이 올바르게 증가되어야 합니다.");
    }

    @Test
    @DisplayName("제품 수량 감소 메서드 테스트(prodAmountDecrease)가 올바르게 수량을 감소시켜야 합니다.")
    void testProdAmountDecrease() {
        MyStorage.products[0] = "TestProduct";
        MyStorage.counts[0] = 10;
        String input = "TestProduct\n5\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        storage.prod_amount_decrease(scanner);

        assertEquals(5, MyStorage.counts[0], "제품 수량이 올바르게 감소되어야 합니다.");
    }

    @Test
    @DisplayName("수량 증가 메서드에서 유효성 검증 테스트(숫자가 아닐 경우)")
    void testProdAmountAddWithInvalidInput() {
        MyStorage.products[0] = "TestProduct";
        MyStorage.counts[0] = 5;
        String input = "TestProduct\nabc\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        storage.prod_amount_add(scanner);

        assertEquals(5, MyStorage.counts[0], "유효하지 않은 입력으로 수량이 변경되지 않아야 합니다.");
    }

    @Test
    @DisplayName("수량 감소 메서드에서 유효성 검증 테스트(숫자가 아닐 경우)")
    void testProdAmountDecreaseWithInvalidInput() {
        MyStorage.products[0] = "TestProduct";
        MyStorage.counts[0] = 10;
        String input = "TestProduct\nxyz\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        storage.prod_amount_decrease(scanner);

        assertEquals(10, MyStorage.counts[0], "유효하지 않은 입력으로 수량이 변경되지 않아야 합니다.");
    }

    @Test
    @DisplayName("남은 수량보다 더 많이 출고할 수 없습니다.")
    void testProdAmountDecreaseExceedingStock() {
        MyStorage.products[0] = "TestProduct";
        MyStorage.counts[0] = 5; // 현재 수량을 5로 설정

        // 제품 이름과 초과하는 출고량
        String input = "TestProduct\n10\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        storage.prod_amount_decrease(scanner);

        assertEquals(5, MyStorage.counts[0], "출고량이 초과되었을 때 수량이 변경되지 않아야 합니다.");
        assertFalse(scanner.hasNext(), "모든 입력이 처리되었어야 합니다.");
    }
}
