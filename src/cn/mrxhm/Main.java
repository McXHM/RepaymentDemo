package cn.mrxhm;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    private JPanel input;
    private GridLayout gridLayout;
    private Font font;
    private JTextField rField;
    private JTextField nField;
    private JTextField pField;
    private double r;
    private int n;
    private double p;
    private JTextArea result;

    public static void main(String[] args) {
        new Main().launch();
    }

    public void launch() {
        font = new Font("微软雅黑", Font.PLAIN, 16);
        setSize(new Dimension(480, 360));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("等额本金/本息计算");
        setFont(font);
        gridLayout = new GridLayout(1, 2);

        JPanel root = new JPanel();
        root.setLayout(new GridLayout(2, 1));
        add(root);

        /*
        输入模块，包含
        - 贷款总额
        - 贷款月数
        - 月利率
        - 计算按钮
         */
        input = new JPanel();
        input.setLayout(new GridLayout(4, 1));

        //贷款总额
        getP();
        //还款月数
        getN();
        //月利率
        getR();
        //计算按钮
        getButton();

        root.add(input);

        result = new JTextArea("");
        result.setFont(font);
        result.setLineWrap(true);
        result.setWrapStyleWord(true);
        JScrollPane jsp = new JScrollPane(result);
        root.add(jsp);

        setVisible(true);
    }

    private void getButton() {
        JButton button = new JButton("计算");
        button.setFont(font);
        button.addActionListener(e -> {
            try {
                r = Double.parseDouble(rField.getText());
                n = Integer.parseInt(nField.getText());
                p = Double.parseDouble(pField.getText());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            boolean effective = (r > 0) && (n > 0) && (p > 0);
            StringBuilder sb = new StringBuilder();
            if(effective) {
                double v1 = p / n;
                double v2 = p * r * (n + 1) / 2;
                double v3 = p * r * (Math.pow(1 + r, n) / ((Math.pow(1 + r, n) - 1)));
                double v4 = n * v3 - p;
                sb.append(String.format("若采用等额本息模型，则每月应还款%.2f元，总利息为%.2f元\n", v3, v4));
                sb.append(String.format("若采用等额本金模型，则每月实际还款本金为%.2f元，总利息为%.2f元，具体每月还款额如下：\n", v1, v2));
                for (int i = 1; i <= n; i++) {
                    sb.append(String.format("第%d月：\t%.2f元\n", i, p / n + (p - p / n * (i - 1)) * r));
                }
            }else sb.append("无效参数");
            result.setText(sb.toString());
        });
        input.add(button);
    }

    /**
     * 获取月利率
     */
    private void getR() {
        JPanel rPanel = new JPanel();
        rPanel.setLayout(gridLayout);
        JLabel rLabel = new JLabel("月利率 R");
        rLabel.setFont(font);
        rPanel.add(rLabel);
        rField = new JTextField(20);
        rField.setFont(font);
        rPanel.add(rField);
        input.add(rPanel);
    }

    /**
     * 获取还款月数
     */
    private void getN() {
        JPanel nPanel = new JPanel();
        nPanel.setLayout(gridLayout);
        JLabel nLabel = new JLabel("还款月数 N");
        nLabel.setFont(font);
        nPanel.add(nLabel);
        nField = new JTextField(20);
        nField.setFont(font);
        nPanel.add(nField);
        input.add(nPanel);
    }

    /**
     * 获取贷款总额
     */
    private void getP() {
        JPanel pPanel = new JPanel();
        pPanel.setLayout(gridLayout);
        JLabel pLabel = new JLabel("贷款总额 P");
        pLabel.setFont(font);
        pPanel.add(pLabel);
        pField = new JTextField(20);
        pField.setFont(font);
        pPanel.add(pField);
        input.add(pPanel);
    }
}