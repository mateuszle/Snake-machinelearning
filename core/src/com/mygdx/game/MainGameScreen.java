package com.mygdx.game;



import java.io.BufferedWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;


public class MainGameScreen implements Screen {

	Snake game;
	public static final int SPEED=15;
	
	Random generator = new Random();
	SpriteBatch batch;
	SpriteBatch scoreSB;
	BitmapFont font;
	Texture background;
	Texture paused;
	Texture appleT;
	Texture gameOverd;
	//MainMenuScreen menu;
	ShapeRenderer head;
	ShapeRenderer eyes;
	ShapeRenderer apple;
	ShapeRenderer wall;
	
	Qlearn q=new Qlearn();
	

	
	// ShapeRenderer[] tail;
	//lista z obietkami wyswietlajacymi poszczegolne ogony wê¿a
	List<ShapeRenderer> lista = new ArrayList();
	
	
	Rectangle rec;
	Rectangle rec2;
	Rectangle[] rec3 = new Rectangle[500];
	int headXPixel;
	int headYPixel;
	int headX;
	int headY;
	int tura;
	int ruch;
	int score;
	int lenght;
	// List<Integer> tailX =new ArrayList();
	// List<Integer> tailY =new ArrayList();
	int[] tailX = new int[500];
	int[] tailY = new int[500];
	
	boolean up;
	boolean down;
	boolean left;
	boolean right;
	
	int appleX;
	int appleY;
	
	float W;
	float H;
	
	float WALL_X;
	float WALL_Y;
	
	int WALL_X_2;
	int WALL_Y_2;
	
	static int szerokosc_planszy;
	static int wysokosc_planszy;
	
	boolean gameOver;
	boolean isPaused;
	static boolean isPlayed;
	
	int score_bez_jablka;
	
	public MainGameScreen(Snake game) throws IOException{
		this.game=game;
		q.init_Q_matrix();
		tura=0;
		ruch=0;
		//q.init_Q_matrix2();
		//q.init_R_matrix();
		
	}
	
	@Override
	public void show() {
		Random rand=new Random();
		W = Gdx.graphics.getWidth();
		H = Gdx.graphics.getHeight();
		score_bez_jablka=0;
		szerokosc_planszy=10;
		wysokosc_planszy=20;
		WALL_X=szerokosc_planszy*15;
		WALL_Y=wysokosc_planszy*15;
	    WALL_X_2=105;
		WALL_Y_2=105;
		//textury
		scoreSB = new SpriteBatch();
		batch = new SpriteBatch();
		appleT = new Texture("apple.png");
		
		//paused = new Texture("paused.png");
		//background = new Texture("QuvaLak.jpg");
		//gameOverd = new Texture("gameover.png");
		
		
		
		// set font
		font = new BitmapFont(Gdx.files.internal("moja.fnt"));
		
        //ustawianie wê¿a
		headXPixel=15;
		headYPixel=15;

		headX = WALL_X_2+(5*headXPixel);
		headY = WALL_Y_2+(5*headYPixel);
		wypiszMaxRuch();
		ruch=0;
		tura++;
		score = 0;
		lenght = 9;
		tailX[0] = headX;
		tailY[0] = headY;

		head = new ShapeRenderer();
		eyes = new ShapeRenderer();
		apple = new ShapeRenderer();
		wall = new ShapeRenderer();
		gameOver = false;
		// pause=false;
		isPlayed=false;
		isPaused = false;
		up =true;
		down = false;
		left = false;
		right = false;
		
		while (true) {
			int randX=rand.nextInt(9);
			int randY=rand.nextInt(19);
			appleX =  WALL_X_2+(randX*headXPixel);
			appleY = WALL_Y_2+(randY*headYPixel);
			if (appleX % 15 == 0 && appleY % 15 == 0)
				break;

		}
	//	appleX =  WALL_X_2+(5*headXPixel);
	//	appleY = WALL_Y_2+(11*headYPixel);
		
		rec = new Rectangle(headX, headY, 15, 15);
		rec2 = new Rectangle(appleX, appleY, 15, 15);
		
		// tworzenie ogona
		for (int i = 0; i < lenght; i++) {
			lista.add(new ShapeRenderer());
		}


		
	}

	@Override
	public void render(float delta) {
	
		try {
			 if (!isPaused && !gameOver)
				update();
			else if (isPaused) {
				// pause();
				if (Gdx.input.isKeyPressed(Keys.SPACE)) {
					isPaused = false;
					Thread.sleep(200);
				}

			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		// rysowanie punktow
		font.draw(batch,"SCORE:" +Integer.toString(score), 15, 750);
		font.draw(batch,"TURA: "+ Integer.toString(tura),400,750);
		font.draw(batch,"RUCH: "+ Integer.toString(ruch),200,750);

		if (isPaused)
			batch.draw(paused, 50, 450);
		// rysowanie napisu gameover
		if (gameOver){
			batch.draw(gameOverd, 50, H / 2);
			font.draw(batch,"PRESS ENTER TO START AGAIN" , 37,410);
		}
			
		// rysowanie jablka
		batch.draw(appleT, appleX, appleY, 15, 15);
		batch.end();

		// rysowanie scian
		wall.begin(ShapeType.Line);
		wall.setColor(Color.WHITE);
		wall.rect(WALL_X_2, WALL_Y_2, WALL_X, WALL_Y);
		wall.end();

		// rysowanie glowy weza
		head.begin(ShapeType.Filled);
		head.setColor(Color.YELLOW);
		head.circle(headX + 7, headY + 7, 9);
		head.end();

		//rysowanie oczu wê¿a
		eyes.begin(ShapeType.Filled);
		eyes.setColor(Color.BLACK);
		eyes.circle(headX + 3, headY + 10, 3);
		eyes.circle(headX + 11, headY + 10, 3);
		eyes.end();
		
		// rysowanie ogona
		for (int i = 2; i < lenght - 3; i++) {

			lista.get(i).begin(ShapeType.Filled);
			lista.get(i).setColor(Color.GREEN);
			// lista.get(i).rect(tailX[i],tailY[i],15,15);
			lista.get(i).circle(tailX[i] + 7, tailY[i] + 7, 7);

			lista.get(i).end();
		}

		//rysowanie planszy gameover jesli gracz przegral
		if (gameOver) {
			isOver();
		}
	}
	
	
		
		
		
	
	public void update() throws InterruptedException, IOException{
		Random rand=new Random();
		ruch++;
		//znajdowanie kwadratu
		int stateApple=0;
		int nextState=0;
		int state=0;
		for(int j=0;j<20;j++){
			for(int i=0;i<10;i++){
				if(headX==105+(15*i)&&headY==105+(15*j)){
					state=i+(j*10);
				}
				if(appleX==105+(15*i)&&appleY==105+(15*j)){
					stateApple=i+(j*10);
				}
			}
		}
		
		int maxIndex;
		//double maxValue=q.maxQ(state);
	
		double e2=0.2;
		if(tura<1000)
			e2=0.2;
		else
			e2=0.9;
		
		double e=rand.nextDouble();
		if(e>e2)
			maxIndex=rand.nextInt(3);
		else
			 maxIndex=q.maxIndex(state,q.Q);
		//wybieranie akcji ze state'u
//		if(tura<1){
//			maxIndex=rand.nextInt(4);
//		}
//		else 
//			maxIndex=q.maxIndex(state,q.Q);
//			
//		if(maxValue==0.0){
//			maxIndex=rand.nextInt(4);
//			
//		}	
//		else
		  // maxIndex=q.maxIndex(state,q.Q);
//		
		
        
       

		//poruszanie bota z uwzglednieniem qmatrix
		moveBot(maxIndex);
		
		int reward=0;
		int action=maxIndex;

		
		//q.reward_table[state][action]=reward;
		

		
		int dif_x=(headX/15)-105-((appleX/15)-105);
		int dif_y=((headY/15)-105)-((appleY/15)-105);
		double max_dist1=Math.sqrt(400);
		max_dist1= Math.sqrt((dif_x*dif_x)+(dif_y*dif_y));
		
		
		
		//aktualizacja pozycji wê¿a
		if (up)
			headY += SPEED;
		if (down)
			headY -=  SPEED ;
		if (left)
			headX -=  SPEED ;
		if (right)
			headX += SPEED ;

		// ustawianie 1 ogona na pozycji g³owy
		tailX[0] = headX;
		tailY[0] = headY;
		
		


		// tworzenie obiektow typu rectangle do kolizjii
		rec = new Rectangle(headX, headY, 15, 15);
		rec2 = new Rectangle(appleX, appleY, 15, 15);

		//state po wykonaniu akcji
		for(int j=0;j<20;j++){
			for(int i=0;i<10;i++){
				if(headX==105+(15*i)&&headY==105+(15*j)){
					nextState=i+(j*10);
				}
			}
		}
		
		//  # Get distance from snake to apple
			 dif_x=(headX/15)-105-((appleX/15)-105);
			 dif_y=((headY/15)-105)-((appleY/15)-105);
			double max_dist2=Math.sqrt(400);
			max_dist2=Math.sqrt((dif_x*dif_x)+(dif_y*dif_y));
			if(max_dist2>max_dist1)
				reward=0;
			else
				reward=1;
			q.reward_table[state][action]=reward;
			q.calculateQ(state,action,nextState,reward);
		
		
		// kolizja z jablkiem
		if (rec.overlaps(rec2)) {
			
			lenght += 2;
			for (int i = 0; i < 2; i++) {
				lista.add(new ShapeRenderer());
			}
			//zwiêksz punkty gracza
			score += 5;

			while (true) {
				int randX=rand.nextInt(9);
				int randY=rand.nextInt(19);
				appleX =  WALL_X_2+(randX*headXPixel);
				appleY = WALL_Y_2+(randY*headYPixel);
				if (appleX % 15 == 0 && appleY % 15 == 0)
					break;

			}

			for (int i = 0; i < lenght - 4; i++) {
				System.out.println(tailX[i]);
			}

		     reward=10;
			 action=maxIndex;
			 q.reward_table[state][action]=reward;
			q.calculateQ(state,action,nextState,reward);
		}
		
		
		// zderzenia z ogonem
		for (int i = 1; i < lenght - 3; i++) {

			if (tailX[i] == headX && tailY[i] == headY) {
				reward=-1;
				q.reward_table[state][action]=reward;
				q.calculateQ(state,action,nextState,reward);
		        dispose();

				show();
			}

		}
		
		// tworzenie ogona
				for (int i = lenght; i > 0; i--) {
					tailX[i] = tailX[i - 1];
					tailY[i] = tailY[i - 1];
				}
				

		
		
		// zderzenia ze scian¹
		if (headX <= (WALL_X_2-2) || headX >= (WALL_X_2+WALL_X) || headY <= (WALL_Y_2-2) || headY >=(WALL_Y_2+WALL_Y)){
			
			reward=-1;
			q.reward_table[state][action]=reward;
			q.calculateQ(state,action,nextState,reward);

			 
			//wypisywanie reward table po gameover
			System.out.printf("%25s", "Action: ");
		        System.out.printf("UP  DOWfN LEFT RIGHT");
		        
		        System.out.println();

		        for (int i = 0; i < 200; i++) {
		            System.out.print("Possible states from " + i + " :[");
		            for (int j = 0; j < 3; j++) {
		                System.out.printf("%4s", q.reward_table[i][j]);
		            }
		            System.out.println("]");
		        }


		        
//		        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
//		                new FileOutputStream("C:/Users/Matson/Documents/SnakeProject/core/src/com/mygdx/game/maze3.txt"), "utf-8"))) {
//		        	
//		        	for (int i = 0; i < 200; i++) {
//				          
//				            for (int j = 0; j < 4; j++) {
//				            	  writer.write( new Integer(q.board[i][j]).toString()+ " ");
//				            }
//				           writer.write("\r\n");
//				          // writer.write();
//				        }
//		  } catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		        dispose();
			show();
		}
			
//		
		
		//wypisz info
		System.out.println();
		System.out.println("APPLESTATE: "+stateApple);
		System.out.println("STATE: "+state);
		System.out.println("NEXTSTATE: "+nextState);
		System.out.println("DIFX: "+dif_x+ " DIFY "+dif_y + " maxdist1 " + max_dist1+ " maxdist2 " + max_dist2);	
		System.out.println("MAX INDEX"+maxIndex);
		
     
		
		//plansza z pauz¹ gry
		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			isPaused = true;
			Thread.sleep(200);
		}
		
		if (Gdx.input.isKeyPressed(Keys.ENTER)) {

			for (int i = 0; i < 500; i++) {
				tailY[i] = -111;
				tailX[i] = -111;
			}
			//ponowna inicjalizacji obiektow wartosciami defaultowymi
			q.init_Q_matrix();
			tura=0;
			ruch=0;
			show();
			gameOver = false;
		}
		
		//zwiekszanie lvl gry
		//poczatkowa szybkosc
		if(score<=7)
		Thread.sleep(1);
		//lvl2
		if(score>7&&score<=15)
			Thread.sleep(40);
		if(score>15)
			Thread.sleep(35);
		
		int previousState = state;

	}
	
	public void wypiszMaxRuch(){
		
		try(FileWriter fw = new FileWriter("C:/Users/Matson/Documents/SnakeProject/core/src/com/mygdx/game/ruch.txt", true);
			    BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter out = new PrintWriter(bw))
			{
			    out.println(new Integer(ruch).toString()+ " ");
			    //more code
			    
			    //more code
			} catch (IOException e) {
			    //exception handling left as an exercise for the reader
			}
		
//		   try (Writer writer = new BufferedWriter(new OutputStreamWriter(
//			        new FileOutputStream("C:/Users/Matson/Documents/SnakeProject/core/src/com/mygdx/game/ruch.txt"), "utf-8"))) {
//				
//			
//			          
//			           
//			           writer.write(new Integer(ruch).toString()+ " ");
//			            
//			           writer.write("\r\n");
//			          // writer.write();
//			        
//			} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			}
	}

	public void moveBot(int maxIndex){
		
		//dwa kierunki
		//lewo
		 if(maxIndex==0){
			 if(up){
				 up = false;
				 down = false;
				 left = true;
				 right = false;
				 
			 }

			 else if(down){
					up = false;
					down = false;
					left = false;
					right =true;
				}
			 else if(left){
					up = false;
					down = true;
					left = false;
					right =false;
				}
			 else if(right){
					up = true;
					down = false;
					left = false;
					right =false;
				}
					
			}
		 
		 //prawo
		 if(maxIndex==1){
			 if(up){
				 up = false;
				 down = false;
				 left = false;
				 right = true;
			 }

			 else if(down){
					up = false;
					down = false;
					left = true;
					right =false;
				}
			 else if(left){
					up = true;
					down = false;
					left = false;
					right =false;
				}
			 else if(right){
					up =false;
					down = true;
					left = false;
					right =false;
				}
					
			}
		 //nic nie rob
		 if(maxIndex==2){
			;			
			}
		 
		 
//		//dó³
//         if(maxIndex==0&& !down){
//			
//			up = true;
//			down = false;
//			left = false;
//			right = false;
//		}
//		//góra
//		if(maxIndex==1&&!up){
//			down = true;
//			up = false;
//			left = false;
//			right = false;
//		}
//		
//		//lewo
//		if(maxIndex==2&& !right){
//			left = true;
//			down = false;
//			up = false;
//			right = false;
//		}
//		
//		//prawo
//		if(maxIndex==3&& !left){
//			right = true;
//			down = false;
//			up = false;
//			left = false;
//		}
	}
	
	public void isOver() {
//			if (Gdx.input.isKeyPressed(Keys.ENTER)) {
//
//			for (int i = 0; i < 500; i++) {
//				tailY[i] = -111;
//				tailX[i] = -111;
//			}
//			//ponowna inicjalizacji obiektow wartosciami defaultowymi
//			q.init_Q_matrix();
//			tura=0;
//			ruch=0;
//			show();
//			gameOver = false;
//		}
	}
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		batch.dispose();
		//background.dispose();
		//font.dispose();
		head.dispose();
		eyes.dispose();
		//paused.dispose();
		//apple.dispose();
		wall.dispose();
		//appleT.dispose();
		
		
	}

}
