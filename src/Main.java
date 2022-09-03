/*
두명의 플레이어가 있고 무한 반복문으로 게임 시작을 물어봅니다.
예스 라면 한명이 주사위를 던집니다.
또 다른 한명이 주사위를 던집니다.
더 큰 수가 나온 사람이 승리 입니다.

노 라면 게임을 종료합니다.
*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
//막
class Japengi{
    HashMap<String, Integer> priceMap=new HashMap<>();
    HashMap<String, Integer> countMap=new HashMap<>();
    Integer inputMoney=0;

    public void addItem(String name, Integer price, Integer count){
        priceMap.put(name, price);
        countMap.put(name, count);
    }

    public void inputMoney(Integer input){
        if(input<0)
            input=0;
        inputMoney=input;
    }

    public void printItems(){
        System.out.println("현재 자판기의 목록");
        System.out.println(priceMap.keySet());
    }

    public void showInputMoney(){
        System.out.println("입력받은 돈: "+inputMoney);
    }

    public Integer purchase(String item){
        System.out.println(item);
        if(!priceMap.containsKey(item)){
            System.out.println("잘못된 아이템 이름입니다!");
            return -1;
        }

        System.out.println(priceMap.get(item));
        if(priceMap.get(item)>inputMoney){
            System.out.println("살 수 없습니다! 입력받은 돈이 부족해요");
            return -1;
        }

        if(countMap.get(item)==0){
            System.out.println("살 수 없습니다! 재고가 부족해요");
            return -1;
        }

        System.out.println(item+"을 구매하였습니다! 거스름돈으로 "+(inputMoney-priceMap.get(item))+"이 반환됩니다.");
        return inputMoney-priceMap.get(item);
    }
}

class Saram {
    Integer money;
    Scanner sc = new Scanner(System.in);

    public void setMoney(Integer don) {
        if(don<0)
            don=0;
        money = don;
        System.out.println("당신은 현재 "+money+"원을 가지고 있습니다!");
    }

    public Integer pay(Integer don){
        if(don>money){
            System.out.println("살 수 없습니다! 당신은 그지거든요!");
            return -1;
        }
        return don;
    }

    public void getCharge(Integer don){
        if(don==-1)
            return;
        money+=don;
    }
}

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public static String input(){
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        //기본세팅들
        Japengi obj = new Japengi();
        obj.addItem("Coke", 2000, 3);
        obj.addItem("Pepsi", 1800, 10);
        obj.addItem("Coffee", 2800, 7);
        obj.addItem("Water", 800, 23);

        Saram person=new Saram();
        person.setMoney(2000);

        while(true){
            System.out.println("\n\n주문하시겠습니까?  yes/no");
            String isOrder= input();

            if (isOrder.equals("yes") || isOrder.equals("Yes")){//주문을 하는 경우 본격적인 주문과정을 거친다.
                System.out.println("동전을 넣어주세요. 얼마를 넣을건가요?");
                obj.inputMoney(person.pay(Integer.parseInt(input())));
                obj.showInputMoney();

                System.out.println("어떤 아이템을 선택하시겠나요? 구매할 아이템의 이름을 입력해주세요");
                obj.printItems();

                person.getCharge(obj.purchase(input()));
                continue;
            } else if(isOrder.equals("no") || isOrder.equals("No")){
                continue;
            } else{
                System.out.println("옳지 않은 입력입니다");
                continue;
            }
        }
    }
}
