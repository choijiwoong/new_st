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
//막 좀더 뭐라고
class Japengi{
    HashMap<String, Integer> priceMap=new HashMap<>();//물품별 가격을 저장
    HashMap<String, Integer> countMap=new HashMap<>();//물품별 재고를 저장
    Integer inputMoney=0;//자판기에 입력받은 돈을 의미. 초기값을 0

    public void addItem(String name, Integer price, Integer count){//물품 이름, 가격, 재고를 입력하면 아이템을 추가한다.
        priceMap.put(name, price);
        countMap.put(name, count);
    }

    public void inputMoney(Integer input){//돈을 입력받으면 올바른 값인지 확인 후 inputMoney에 저장한다.
        if(input<0)
            input=0;
        inputMoney=input;
    }

    public void printItems(){//자판기의 목록과 가격을 출력해준다.
        System.out.println("현재 자판기의 목록");
        System.out.println(priceMap.toString());
    }

    public void showInputMoney(){
        System.out.println("입력받은 돈: "+inputMoney);
    }//inputMoney 메서드를 이용하여 입력받은 돈을 확인할 수 있다.

    public Integer purchase(String item){//아이템 결제를 수행한다.
        if(!priceMap.containsKey(item)){//자판기에 해당 물품이 없는 경우
            System.out.println("잘못된 아이템 이름입니다!");
            return -1;
        }

        if(priceMap.get(item)>inputMoney){//자판기가 입력받은 돈보다 물건의 값이 더 비싼경우
            System.out.println("살 수 없습니다! 입력받은 돈이 부족해요");
            return -1;
        }

        if(countMap.get(item)==0){//재고가 없는 걍우
            System.out.println("살 수 없습니다! 재고가 부족해요");
            return -1;
        }

        System.out.println(item+"을 구매하였습니다! 거스름돈으로 "+(inputMoney-priceMap.get(item))+"원이 반환됩니다.");
        return -priceMap.get(item);//**거스름돈은 위의 출력식과 같지만, 실제 결제한 금액을 아직 사람에게서 빼지 않았기 때문에 -inputMoney를 수행해야해서 생략했다.**
    }
}

class Saram {
    Integer money;//보유재산

    public void setMoney(Integer don) {//보유재산을 설정한다. 음의 값인 경우 0으로 세팅한다.
        if(don<0)
            don=0;
        money = don;
        System.out.println("당신은 현재 "+money+"원을 가지고 있습니다!");
    }

    public Integer pay(Integer don){//입력받은 금액이 보유자산을 초과하지 않으면 반환한다.
        if(don>money){
            System.out.println("입력하려는 돈이 소유중인 돈보다 많습니다!");
            return -1;
        }
        return don;
    }

    public void getCharge(Integer don){//입력받은 돈을 보유재산에 더한다.
        if(don<0)
            return;
        money+=don;
    }
}

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));//입력에 사용할 리더
    public static String input(){//입력을 해당 함수를 통해 진행한다.
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        //기본세팅들
        Japengi vendingMachine = new Japengi();//자판기 객체 생성 및 아이템 추가
        vendingMachine.addItem("Coke", 2000, 3);
        vendingMachine.addItem("Pepsi", 1800, 10);
        vendingMachine.addItem("Coffee", 2800, 7);
        vendingMachine.addItem("Water", 800, 23);

        Saram person=new Saram();//사람 객체 생성 및 보유재산 입력
        System.out.println("초기 보유자산을 입력해주세요!");
        try{//초기 자산입력이 숫자가 아닌경우를 위한 예외처리
            person.setMoney(Integer.parseInt(input()));
        } catch(NumberFormatException e){
            System.out.println("숫자를 입력하셔야 합니다!");
            return;
        }

        while(true){
            System.out.println("\n\n현재 재산: "+person.money);//보유재산을 매 주문시마다 출력
            System.out.println("주문하시겠습니까?  yes/no");
            String isOrder= input();

            if (isOrder.equals("yes") || isOrder.equals("Yes")){//주문을 하는 경우
                System.out.println("동전을 넣어주세요. 얼마를 넣을건가요?");
                Integer money;
                try {//문자를 입력하는 경우를 대비한 예외처리
                    money = person.pay(Integer.parseInt(input()));
                } catch(NumberFormatException e){
                    System.out.println("숫자를 입력하셔야 합니다!");
                    continue;
                }
                if(money==-1)//person.pay에서 입력받은 인자가 보유재산보다 큰 경우 -1을 반환하게 하였다.
                    continue;
                vendingMachine.inputMoney(money);//위의 예외처리과정을 거쳐 문자가 아니고 보유재산보다 적은 수인 money값을 자판기에 넣는다.
                vendingMachine.showInputMoney();//자판기는 입력받은 돈을 출력한다.

                System.out.println("어떤 아이템을 선택하시겠나요? 구매할 아이템의 이름을 입력해주세요");
                vendingMachine.printItems();//자판기의 가격 딕셔너리를 출력하여 사용자가 아이템목록과 가격을 확인할 수 있게 한다.

                Integer charge=vendingMachine.purchase(input());
                if(charge==-1)//입력받은 돈으로 자판기 물품 구매가 싪패했을 경우
                    continue;

                person.getCharge(charge);//입력받은 아이템이름이 존재한다면 물품 구매 후 거스름돈을 반환하고, 해당 돈을 사람에게 돌려준다.
                return;
            }
            if(isOrder.equals("no") || isOrder.equals("No")){
                return ;
            }
            System.out.println("옳지 않은 입력입니다!");
        }
    }
}
