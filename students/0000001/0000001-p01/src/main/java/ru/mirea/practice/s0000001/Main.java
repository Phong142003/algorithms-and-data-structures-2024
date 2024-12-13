package ru.mirea.practice.s0000001;

public class Main {
    enum State {
        S0, S1, S2, S3
    }

    static class Result {
        State nextState;
        int output;
        Result(State nextState, int output) {
            this.nextState = nextState;
            this.output = output;
        }
    }
    static Result nextStateAndOutput(State current, int input) {
        switch (current) {
            case S0:
                if (input == 2) { // X2|Y2 -> S3
                    return new Result(State.S3, 2);
                } else if (input == 5) { // X5|Y3 -> S1
                    return new Result(State.S1, 3);
                }
                break;

            case S1:
                if (input == 5) { // X5|Y3 -> S0
                    return new Result(State.S0, 3);
                } else if (input == 2) {
                    // Ở đây mô hình vẽ cả X2|Y1->S2 và X2|Y2->S3
                    // Cần điều kiện phụ. Giả sử ta ưu tiên X2|Y1->S2 (thêm một điều kiện thứ 2)
                    // Nếu cần, ta có thể dựa trên một biến trạng thái phụ hoặc một logic khác.
                    // Tạm chọn X2|Y1->S2 là đường đi mặc định.
                    return new Result(State.S2, 1);
                }
                break;

            case S2:
                if (input == 2) { // X2|Y0 -> S2
                    return new Result(State.S2, 0);
                } else if (input == 5) { // X5|Y3 -> S0
                    return new Result(State.S0, 3);
                }
                break;

            case S3:
                if (input == 5) {
                    // Ở đây có X5|Y5->S3 (vòng lặp) và X5|Y3->S0
                    // Cần điều kiện phụ. Ví dụ, nếu ta có thêm 1 cờ boolean
                    // để chọn xem khi nào thì ở lại S3 và khi nào thì về S0.
                    // Tạm giả sử nếu input == 5 lần đầu thì Y5->S3,
                    // lần sau thì Y3->S0. Ở đây, để đơn giản, luôn về S3.
                    return new Result(State.S3, 5);
                } else if (input == 2) { // X2|Y2->S1
                    return new Result(State.S1, 2);
                }
                break;
        }
        return new Result(current, -1); // Không xác định
    }

    public static void main(String[] args) {
        State current = State.S0;
        int[] inputs = {2,5,2,2,5};
        for (int inp : inputs) {
            Result r = nextStateAndOutput(current, inp);
            System.out.println("Current: " + current + ", Input: X" + inp +
                " -> Output: Y" + r.output + ", Next State: " + r.nextState);
            current = r.nextState;
        }
    }
}
