package views;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Background extends JPanel{
	
		Image imageOrg = null;
		Image image = null;
		{
			addComponentListener(new ComponentAdapter() {
				@Override
				public void componentResized(ComponentEvent e) {
					int w = Background.this.getWidth();
					int h = Background.this.getHeight();
					image = w > 0 && h > 0 ? imageOrg.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH) : imageOrg;
					Background.this.repaint();
				}
			});
		}

		public Background(Image i) {
			imageOrg = i;
			image = i;
			setOpaque(false);
		}

		@Override
		public void paint(Graphics g) {
			if (image != null)
				g.drawImage(image, 0, 0, null);
			super.paint(g);
		}
}
