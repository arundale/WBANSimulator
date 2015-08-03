package wban.simulate.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

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
    final JPopupMenu popMenu = new JPopupMenu();
    final Border lineBorder = BorderFactory.createLineBorder(Color.black);

    JLabel currentIcon = null;
    int currentX, currentY;

    public SwingViewer() {
        JMenuItem miChangeDetails = new JMenuItem(strMIChangeDetails);
        JMenuItem miChargeBattery = new JMenuItem(strMIChargeBattery);
        JMenuItem miStopCharging = new JMenuItem(strMIStopCharging);
        JMenuItem miBatteryDown = new JMenuItem(strMIBatteryDown);
        miChangeDetails.addActionListener(this);
        miChargeBattery.addActionListener(this);
        miStopCharging.addActionListener(this);
        miBatteryDown.addActionListener(this);
        popMenu.add(miChangeDetails);
        popMenu.add(miChargeBattery);
        popMenu.add(miStopCharging);
        popMenu.add(miBatteryDown);
    }

    public void run() {
        final JFrame f = new JFrame("WBAN Simulation Viewer");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        f.setBounds(0, 0, (int) screenSize.getWidth(),
                (int) screenSize.getHeight());
        this.setLayout(null);
        this.setSize((int) screenSize.getWidth(), (int) screenSize.getHeight());
        final JPopupMenu mainMenu = new JPopupMenu();
        JMenuItem miBase = new JMenuItem(strMIAddBaseStation);
        miBase.addActionListener(this);
        JMenuItem miSensorCluster = new JMenuItem(strMIAddSensorCluster);
        miSensorCluster.addActionListener(this);
        mainMenu.add(miBase);
        mainMenu.add(miSensorCluster);
        this.add(mainMenu);
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                if (evt.isPopupTrigger()) {
                    mainMenu.show(evt.getComponent(), evt.getX(), evt.getY());
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
    }

    public void actionPerformed(ActionEvent e) {
        String strCommand = e.getActionCommand();
        if (strCommand.equals(strMIAddBaseStation)) {
            JLabel icon = new JLabel(baseStationImg);
            icon.setBounds(currentX, currentY, baseStationImg.getIconWidth(),
                    baseStationImg.getIconHeight());
            icon.addMouseListener(this);
            icon.addMouseMotionListener(this);
            this.add(icon);
            if (currentIcon != null)
                currentIcon.setBorder(null);
            currentIcon = icon;
            icon.setBorder(lineBorder);
        } else if (strCommand.equals(strMIAddSensorCluster)) {
            JLabel icon = new JLabel(sensorClusterNormalImg);
            icon.setBounds(currentX, currentY,
                    sensorClusterNormalImg.getIconWidth(),
                    sensorClusterNormalImg.getIconHeight());
            icon.addMouseListener(this);
            icon.addMouseMotionListener(this);
            this.add(icon);
            if (currentIcon != null)
                currentIcon.setBorder(null);
            currentIcon = icon;
            icon.setBorder(lineBorder);
            popMenu.setVisible(false);
            popMenu.revalidate();
        } else if (strCommand.equals(strMIChargeBattery)) {
            currentIcon.setIcon(sensorClusterBatteryChargingImg);
            this.revalidate();
        } else if (strCommand.equals(strMIStopCharging)) {
            currentIcon.setIcon(sensorClusterNormalImg);
            this.revalidate();
        } else if (strCommand.equals(strMIBatteryDown)) {
            currentIcon.setIcon(sensorClusterBatteryDownImg);
            this.revalidate();
        }
        this.repaint();
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
            }
        }
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getComponent() instanceof JLabel) {
            if (currentIcon != null) {
                currentIcon.setBorder(null);
            }
            currentIcon = (JLabel) e.getComponent();
            currentIcon.setBorder(lineBorder);
        }
    }

    public void mousePressed(MouseEvent e) {
        if (e.isPopupTrigger() && e.getComponent().equals(currentIcon)) {
            popMenu.setVisible(true);
            popMenu.show(e.getComponent(), e.getX(), e.getY());
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
