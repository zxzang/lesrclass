package Charting;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartPanel;

public class ChartViewer extends JFrame{
		
	private static final long serialVersionUID = 1L;

	java.awt.Image image;
	boolean stretched = true;
	int xCoordinate;
	int yCoordinate;
	Image bImage;
	

	public ChartViewer(String filename){
		
		
		ImageIcon icon = new ImageIcon(filename);
		setLayout(new GridLayout(1,1,1,1));
		add (new JLabel(icon));
		setVisible(true);
		setSize(1280,790);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

	}
	
	public ChartViewer(JFreeChart chart) {
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1280, 790));
		chartPanel.setMouseZoomable(true, false);
		setContentPane(chartPanel);				
		setSize(1280,790);

		setVisible(true);
		
//		bImage = chart.createBufferedImage(1280, 790);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	public void paintComponent(Graphics g){
		
//		super.paintComponent(g);
		if(bImage != null) g.drawImage(bImage, 0, 0, getWidth(), getHeight(), this);
		
	}
		public java.awt.Image getImage(){
		return image;
		
	}
	
	public void setImage(java.awt.Image image){
		this.image = image;
		repaint();		
	}
		
	public int getXCoordinate(){
		return xCoordinate;
	}
	
	public void setXCoordinate(int xCoordinate){
		this.xCoordinate = xCoordinate;
		repaint();
	}
	
	public int getYCoordinate(){
		return yCoordinate;
	}
	
	public void setYCoordinate(int yCoordinate){
		this.yCoordinate = yCoordinate;
		repaint();
	}
	
}
