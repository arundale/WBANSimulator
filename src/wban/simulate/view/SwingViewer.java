package wban.simulate.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import wban.simulate.Simulator;
import wban.simulate.config.BaseStationConfig;
import wban.simulate.config.SlaveConfig;
import wban.simulate.path.BoundingSquare;
import wban.simulate.path.PointSet;
import wban.simulate.path.Pt;

public class SwingViewer extends JPanel implements Runnable, ActionListener,
        MouseMotionListener, MouseListener {

    static final long serialVersionUID = 474227420717631309L;
    ImageIcon baseStationImg = new ImageIcon(this.getClass().getResource(
            "/images/BaseStationIcon.png"));
    ImageIcon sensorClusterNormalImg = new ImageIcon(this.getClass()
            .getResource("/images/SensorClusterIcon.png"));
    ImageIcon sensorClusterBatteryChargingImg = new ImageIcon(this.getClass()
            .getResource("/images/SensorClusterBatteryChargingIcon.png"));
    ImageIcon sensorClusterBatteryDownImg = new ImageIcon(this.getClass()
            .getResource("/images/SensorClusterBatteryDownIcon.png"));
    static final String strMIAddBaseStation = "Add Base Station";
    static final String strMIAddSensorCluster = "Add Sensor Cluster";
    static final String strMIChangeDetails = "Change details";
    static final String strMIChargeBattery = "Charge battery";
    static final String strMIStopCharging = "Stop Charging";
    static final String strMIBatteryDown = "Battery down";
    static final String strMISendData = "Send data";
    static final String strMILoadConfig = "Load from Configuration";
    static final String strMISaveConfig = "Save Configuration";
    final JPopupMenu popMenu = new JPopupMenu();
    final Border lineBorder = BorderFactory.createLineBorder(Color.black);

    JLabel currentIcon = null;
    int currentX, currentY;

    Simulator simulator = Simulator.getInstance();
    private PointSet currentPointSet = null;

    public SwingViewer() {
        JMenuItem miChangeDetails = new JMenuItem(strMIChangeDetails);
        JMenuItem miChargeBattery = new JMenuItem(strMIChargeBattery);
        JMenuItem miStopCharging = new JMenuItem(strMIStopCharging);
        JMenuItem miBatteryDown = new JMenuItem(strMIBatteryDown);
        JMenuItem miSendData = new JMenuItem(strMISendData);
        miChangeDetails.addActionListener(this);
        miChargeBattery.addActionListener(this);
        miStopCharging.addActionListener(this);
        miBatteryDown.addActionListener(this);
        miSendData.addActionListener(this);
        popMenu.add(miChangeDetails);
        popMenu.add(miChargeBattery);
        popMenu.add(miStopCharging);
        popMenu.add(miBatteryDown);
        popMenu.add(miSendData);
    }

    public void run() {
        final JFrame f = new JFrame("WBAN Simulation Viewer");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        f.setBounds(0, 0, (int) screenSize.getWidth(),
                (int) screenSize.getHeight());
        this.setLayout(null);
        this.setSize((int) screenSize.getWidth(), (int) screenSize.getHeight());

        JMenuBar menuBar = new JMenuBar();
        JMenu menuMain = new JMenu("Main");
        menuBar.add(menuMain);
        f.setJMenuBar(menuBar);
        JMenuItem miLoadConfig = new JMenuItem(strMILoadConfig);
        miLoadConfig.addActionListener(this);
        menuMain.add(miLoadConfig);
        JMenuItem miSaveConfig = new JMenuItem(strMISaveConfig);
        miSaveConfig.addActionListener(this);
        menuMain.add(miSaveConfig);

        final JPopupMenu popMenu = new JPopupMenu();
        JMenuItem miBase = new JMenuItem(strMIAddBaseStation);
        miBase.addActionListener(this);
        JMenuItem miSensorCluster = new JMenuItem(strMIAddSensorCluster);
        miSensorCluster.addActionListener(this);
        popMenu.add(miBase);
        popMenu.add(miSensorCluster);
        this.add(popMenu);
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                if (evt.isPopupTrigger()) {
                    popMenu.show(evt.getComponent(), evt.getX(), evt.getY());
                    currentX = evt.getX();
                    currentY = evt.getY();
                }
            }

            public void mouseReleased(MouseEvent evt) {
            }
        });
        JScrollPane scrollPane = new JScrollPane(this);
        scrollPane.setAutoscrolls(true);
        scrollPane.setWheelScrollingEnabled(true);
        f.getContentPane().add(scrollPane);
        f.setVisible(true);
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return screenSize;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (currentPointSet != null) {
            BoundingSquare bs = currentPointSet.getBoundingSquare();
            Pt[] vertices = bs.getBounds();
            g.drawLine(vertices[0].getX(), vertices[0].getY(), vertices[1].getX(), vertices[1].getY());
            g.drawLine(vertices[1].getX(), vertices[1].getY(), vertices[2].getX(), vertices[2].getY());
            g.drawLine(vertices[2].getX(), vertices[2].getY(), vertices[3].getX(), vertices[3].getY());
            g.drawLine(vertices[3].getX(), vertices[3].getY(), vertices[0].getX(), vertices[0].getY());
        }
    }

    private JLabel addBaseStation(int x, int y) {
        JLabel icon = new JLabel(baseStationImg);
        icon.setBounds(x, y, baseStationImg.getIconWidth(),
                baseStationImg.getIconHeight());
        icon.addMouseListener(this);
        icon.addMouseMotionListener(this);
        this.add(icon);
        if (currentIcon != null)
            currentIcon.setBorder(null);
        currentIcon = icon;
        icon.setBorder(lineBorder);
        return icon;
    }

    public JLabel addSensorCluster(int x, int y, short state) {
        ImageIcon img = null;
        switch (state) {
        case SlaveConfig.ST_DISCHARGING:
            img = sensorClusterNormalImg;
            break;
        case SlaveConfig.ST_CHARGING:
            img = sensorClusterBatteryChargingImg;
            break;
        case SlaveConfig.ST_DOWN:
            img = sensorClusterBatteryDownImg;
            break;
        }
        JLabel icon = new JLabel(img);
        icon.setBounds(x, y, img.getIconWidth(), img.getIconHeight());
        icon.addMouseListener(this);
        icon.addMouseMotionListener(this);
        this.add(icon);
        if (currentIcon != null)
            currentIcon.setBorder(null);
        currentIcon = icon;
        icon.setBorder(lineBorder);
        popMenu.setVisible(false);
        popMenu.revalidate();
        return icon;
    }

    public void actionPerformed(ActionEvent e) {
        String strCommand = e.getActionCommand();
        if (strCommand.equals(strMIAddBaseStation)) {
            JLabel icon = addBaseStation(currentX, currentY);
            BaseStationConfig bsConfig = new BaseStationConfig();
            bsConfig.setBSPosX(currentX);
            bsConfig.setBSPosY(currentY);
            bsConfig.setBSMidX(currentX + currentIcon.getWidth() / 2);
            bsConfig.setBSMidY(currentY + currentIcon.getHeight() / 2);
            int bsIdx = simulator.getConfig().addBaseStation(bsConfig);
            icon.setToolTipText(String.valueOf(bsIdx));
        } else if (strCommand.equals(strMIAddSensorCluster)) {
            JLabel icon = addSensorCluster(currentX, currentY,
                    SlaveConfig.ST_DISCHARGING);
            SlaveConfig slaveConfig = new SlaveConfig();
            slaveConfig.setState(SlaveConfig.ST_DISCHARGING);
            slaveConfig.setSlavePosX(currentX);
            slaveConfig.setSlavePosY(currentY);
            slaveConfig.setSlaveMidX(currentX + currentIcon.getWidth() / 2);
            slaveConfig.setSlaveMidY(currentY + currentIcon.getHeight() / 2);
            int slaveIdx = simulator.getConfig().addSlaveConfig(slaveConfig);
            icon.setToolTipText(String.valueOf(slaveIdx));
        } else if (strCommand.equals(strMIChargeBattery)) {
            currentIcon.setIcon(sensorClusterBatteryChargingImg);
            this.revalidate();
            int idx = Integer.valueOf(currentIcon.getToolTipText());
            simulator.getConfig().getSlaveConfig(idx)
                    .setState(SlaveConfig.ST_CHARGING);
        } else if (strCommand.equals(strMIStopCharging)) {
            currentIcon.setIcon(sensorClusterNormalImg);
            this.revalidate();
            int idx = Integer.valueOf(currentIcon.getToolTipText());
            simulator.getConfig().getSlaveConfig(idx)
                    .setState(SlaveConfig.ST_DISCHARGING);
        } else if (strCommand.equals(strMIBatteryDown)) {
            currentIcon.setIcon(sensorClusterBatteryDownImg);
            this.revalidate();
            int idx = Integer.valueOf(currentIcon.getToolTipText());
            simulator.getConfig().getSlaveConfig(idx)
                    .setState(SlaveConfig.ST_DOWN);
        } else if (strCommand.equals(strMISendData)) {
            int id = Integer.valueOf(currentIcon.getToolTipText());
            SlaveConfig sc = simulator.getConfig().getSlaveConfig(id);
            currentPointSet = simulator.buildPathFor(sc);
        } else if (strCommand.equals(strMILoadConfig)) {
            simulator.loadConfig();
            redrawConfig();
        } else if (strCommand.equals(strMISaveConfig)) {
            simulator.saveConfig();
        }
        this.repaint();
    }

    private void redrawConfig() {
        for (Component c : this.getComponents()) {
            if (c instanceof JLabel) {
                JLabel l = (JLabel) c;
                Icon i = l.getIcon();
                if (i.equals(baseStationImg)
                        || i.equals(sensorClusterNormalImg)
                        || i.equals(sensorClusterBatteryChargingImg)
                        || i.equals(sensorClusterBatteryDownImg)) {
                    this.remove(c);
                }
            }
        }
        List<SlaveConfig> lstSlaveConfig = simulator.getConfig()
                .getAllSlaveConfig();
        for (SlaveConfig sc : lstSlaveConfig) {
            JLabel label = addSensorCluster(sc.getSlavePosX(),
                    sc.getSlavePosY(), sc.getState());
            label.setToolTipText(String.valueOf(lstSlaveConfig.indexOf(sc)));
        }
        List<BaseStationConfig> lstBSConfig = simulator.getConfig()
                .getAllBSConfig();
        for (BaseStationConfig bsc : lstBSConfig) {
            JLabel label = addBaseStation(bsc.getBSPosX(), bsc.getBSPosY());
            label.setToolTipText(String.valueOf(lstBSConfig.indexOf(bsc)));
        }
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (currentIcon != null) {
            x += currentIcon.getX();
            y += currentIcon.getY();
            if (x >= 0 && x <= this.getWidth() && y >= 0
                    && y <= this.getHeight()) {
                currentIcon.setBounds(x, y, currentIcon.getWidth(),
                        currentIcon.getHeight());
                if (currentIcon.getIcon().equals(baseStationImg)) {
                    int bsIdx = Integer.parseInt(currentIcon.getToolTipText());
                    BaseStationConfig bsConfig = simulator.getConfig()
                            .getAllBSConfig().get(bsIdx);
                    bsConfig.setBSPosX(x);
                    bsConfig.setBSPosY(y);
                    bsConfig.setBSMidX(x + currentIcon.getWidth() / 2);
                    bsConfig.setBSMidY(y + currentIcon.getHeight() / 2);
                } else {
                    int idx = Integer.parseInt(currentIcon.getToolTipText());
                    SlaveConfig slaveConfig = simulator.getConfig()
                            .getAllSlaveConfig().get(idx);
                    slaveConfig.setSlavePosX(x);
                    slaveConfig.setSlavePosY(y);
                    slaveConfig.setSlaveMidX(x + currentIcon.getWidth()
                            / 2);
                    slaveConfig.setSlaveMidY(y + currentIcon.getHeight()
                            / 2);
                }
            }
        }
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        if (e.getComponent() instanceof JLabel) {
            if (currentIcon != null) {
                currentIcon.setBorder(null);
            }
            currentIcon = (JLabel) e.getComponent();
            currentIcon.setBorder(lineBorder);
            Icon img = currentIcon.getIcon();
            if (e.isPopupTrigger() && img != null
                    && !img.equals(baseStationImg)) {
                popMenu.setVisible(true);
                popMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new SwingViewer());
    }

}
