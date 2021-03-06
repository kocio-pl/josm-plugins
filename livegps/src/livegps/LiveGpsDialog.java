/**
 *
 */
package livegps;

import static org.openstreetmap.josm.tools.I18n.tr;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.openstreetmap.josm.gui.MapFrame;
import org.openstreetmap.josm.gui.dialogs.ToggleDialog;
import org.openstreetmap.josm.tools.Shortcut;

/**
 * @author cdaller
 *
 */
public class LiveGpsDialog extends ToggleDialog implements PropertyChangeListener {
    private static final long serialVersionUID = 6183400754671501117L;
    private JLabel statusLabel;
    private JLabel wayLabel;
    private JLabel latLabel;
    private JLabel longLabel;
    private JLabel courseLabel;
    private JLabel speedLabel;
    private JPanel panel;
    private LiveGpsStatus status;
    private LiveGpsData data;

    /**
     * @param name
     * @param iconName
     * @param tooltip
     * @param shortcut
     * @param preferredHeight
     */
    public LiveGpsDialog(final MapFrame mapFrame) {
        super(tr("Live GPS"), "livegps", tr("Show GPS data."),
        Shortcut.registerShortcut("subwindow:livegps", tr("Toggle: {0}", tr("Live GPS")),
        KeyEvent.VK_G, Shortcut.ALT_CTRL_SHIFT), 100);
        panel = new JPanel();
        panel.setLayout(new GridLayout(6,2));
        panel.add(new JLabel(tr("Status")));
        panel.add(statusLabel = new JLabel());
        panel.add(new JLabel(tr("Way Info")));
        panel.add(wayLabel = new JLabel());
        panel.add(new JLabel(tr("Latitude")));
        panel.add(latLabel = new JLabel());
        panel.add(new JLabel(tr("Longitude")));
        panel.add(longLabel = new JLabel());
        panel.add(new JLabel(tr("Speed")));
        panel.add(speedLabel = new JLabel());
        panel.add(new JLabel(tr("Course")));
        panel.add(courseLabel = new JLabel());
        createLayout(panel, true, null);
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (!isVisible())
            return;

        if("gpsdata".equals(evt.getPropertyName())) {
            data = (LiveGpsData) evt.getNewValue();

            SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if(data.isFix()) {
                    panel.setBackground(Color.WHITE);
                    latLabel.setText(data.getLatitude() + "deg");
                    longLabel.setText(data.getLongitude() + "deg");
                    double mySpeed = data.getSpeed() * 3.6f;
                    speedLabel.setText((Math.round(mySpeed*100)/100) + "km/h");
                    courseLabel.setText(data.getCourse() + "deg");

                    String wayString = data.getWayInfo();
                    if(wayString.length() > 0) {
                        wayLabel.setText(wayString);
                    } else {
                        wayLabel.setText(tr("unknown"));
                    }
                } else {
                    latLabel.setText("");
                    longLabel.setText("");
                    speedLabel.setText("");
                    courseLabel.setText("");
                    panel.setBackground(Color.RED);
                }
            }});
        } else if ("gpsstatus".equals(evt.getPropertyName())) {
            status = (LiveGpsStatus) evt.getNewValue();

            SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                statusLabel.setText(status.getStatusMessage());
                if(status.getStatus() != LiveGpsStatus.GpsStatus.CONNECTED) {
                    panel.setBackground(Color.RED);
                } else {
                    panel.setBackground(Color.WHITE);
                }
            }});
        }
    }
}
