package enjoy2048;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import enjoy2048.grid.GridPlate;

public class Game {
	// View
	// refactoring 꺼리 많다
	GridPlate gridPlate;
	JFrame frame = null;
	JPanel panel = null;

	Game(GridPlate gridPlate) {
		this.gridPlate = gridPlate;
		initialize();
	}
	
///////////////// listening /////////////////////
	public void initialize() {
		frame = new JFrame();
		panel = new JPanel();
		frame.setLayout(new GridBagLayout());
		frame.add(panel, new GridBagConstraints());
		frame.setSize(800, 800);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				synchronized (frame) {
					switch (e.getKeyCode()) {
						case KeyEvent.VK_UP:
							gridPlate.onSwipeToTop();  
							break;
						case KeyEvent.VK_DOWN:
							gridPlate.onSwipeToBottom(); 
							break;
						case KeyEvent.VK_LEFT:
							gridPlate.onSwipeToLeft(); 	
							break;
						case KeyEvent.VK_RIGHT:
							gridPlate.onSwipeToRight(); 
							break;
						default:
							return;
						}
					
					gridPlate.randomFilling(1);
					
					if (gridPlate.isFail()) {
						JOptionPane.showMessageDialog(frame, "Finish!");
						frame.dispose();
					}
					else 
						show();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) { //코드는 이쪽으로?
			}

			@Override
			public void keyTyped(KeyEvent e) { 
			}
		});

		frame.setVisible(true);
	}
	
	///////////////// diaplay /////////////////////
	static Font fontPlain = new Font("Verdana", Font.PLAIN, 20);
	static Font fontMerge = new Font("Verdana", Font.BOLD, 20);
	static Font fontRandom = new Font("Verdana", Font.BOLD, 20);
	static Font fontScoreTitle = new Font(Font.SANS_SERIF, Font.BOLD, 17);
	static Font fontScore = new Font(Font.SANS_SERIF, Font.BOLD, 17);
	
	private void prepareNewGrid(){
		frame.setVisible(false);
		panel.removeAll();
		frame.setLayout(new GridLayout(2,2));
		panel.setLayout(new GridLayout(6, 4));
	}
	

	private void setColor(JLabel numLabel, Index index) {
        int x = gridPlate.getvalue(index);
		switch (x) {
			case 0:
				numLabel.setBackground(Color.WHITE);
				break;
			case 2:
				numLabel.setBackground(new Color(222, 246, 227));
				break;
			case 4:
				numLabel.setBackground(new Color(220, 226, 192));
				break;
			case 8:
				numLabel.setBackground(new Color(190, 207, 228));
				break;
			case 16:
				numLabel.setBackground(new Color(200, 210, 204));
				break;
			case 32:
				numLabel.setBackground(new Color(190, 220, 227));
				break;
			case 64:
				numLabel.setBackground(new Color(200, 230, 190));
				break;
			case 128:
				numLabel.setBackground(new Color(222, 210, 230));
				break;
			case 256:
				numLabel.setBackground(new Color(190, 220, 210));
				break;
			case 512:
				numLabel.setBackground(new Color(222, 230, 200));
				break;
			case 2048:
				numLabel.setBackground(new Color(200, 210, 210));
				break;
			}
	}
 
	
	private void setFont(JLabel numLabel, Index index) {
		numLabel.setOpaque(true);
		numLabel.setHorizontalAlignment(SwingConstants.CENTER);
		numLabel.setVerticalAlignment(SwingConstants.CENTER);
		
		if (gridPlate.isNewMerge(index)){
			numLabel.setForeground(Color.BLUE);
			numLabel.setFont(fontMerge);
		}
		else if (gridPlate.isNewRandom(index) ) {
			numLabel.setForeground(Color.LIGHT_GRAY);
			numLabel.setFont(fontRandom);
		}
		else 
			numLabel.setFont(fontPlain);
	}

	private void displayNumber(Index index) {
		JLabel numLabel = new JLabel();
		setFont(numLabel, index);
		setColor(numLabel, index);
		numLabel.setText(gridPlate.getString(index));
		
		panel.add(numLabel);
	}
	
	private void displayGrids(){
		prepareNewGrid();
		Index.allIndex(4).forEach(x->displayNumber(x));
		gridPlate.clearDataforNext();
	}
	
	
	private void displayEmptyLines() {
		// print empty line
		int some = 6;
		for (int i = 0; i < some; i++)
			panel.add(new JLabel(" "));
	}
	
	private void displayScore() {
		JLabel scoreHeader = new JLabel("Score:");
		scoreHeader.setFont(fontScoreTitle);
		panel.add(scoreHeader);
		
		JLabel scoreLabel = new JLabel(gridPlate.getScoreStr());
		scoreLabel.setFont(fontScore);
		panel.add(scoreLabel);
		
		frame.setSize(300,500);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private void showWinMessage() {
		JOptionPane.showMessageDialog(frame, "You Win!");
		// TODO: "Do more? yes and no ...." 9.13
		// ncube.clearWin(); else
		frame.dispose();
	}

	void show() {
		displayGrids();
		displayEmptyLines(); 
		displayScore();
		if (gridPlate.isWin())
			showWinMessage();		
	}

	public static Game getGame(GridPlate c) {
		return new Game(c);
	}

}
