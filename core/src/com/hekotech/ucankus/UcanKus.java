package com.hekotech.ucankus;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import java.awt.Font;
import java.awt.peer.ScrollbarPeer;
import java.util.Random;

public class UcanKus extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Texture bird;
	Texture ari1,ari2,ari3;
	float bx,by,bw,bh,arix,ariy;
	float sw,sh;
	float gravity = 0.8f;
	float v = 0.0f;
	int state = 0;
	int arisayisi=3;
	float arilarx[] = new float[arisayisi];
	float arilary[][] = new float[3][arisayisi];
	Circle c_bird;
	Circle c_ari1[] =new Circle[arisayisi];
	Circle c_ari2[] =new Circle[arisayisi];
	Circle c_ari3[] =new Circle[arisayisi];
	BitmapFont font;
    BitmapFont font1;
	Sound sound;



	boolean flag=true;
	boolean flag1=true;
	int puan;


	ShapeRenderer sr ;

	float d;


	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("back.png");
		bird = new Texture("bird1.png");
		ari1 = new Texture("bee1.png");
		ari2 = new Texture("bee2.png");
		ari3 = new Texture("bee1.png");
        sw=Gdx.graphics.getWidth();
        sh=Gdx.graphics.getHeight();
		bx=sw/4;
		by=sh/3;
		bw=sw/13;
		bh=sh/10;
		arix=1200;
		font = new BitmapFont();
		font.setColor(Color.RED);
		font.getData().setScale(8);
		sound = Gdx.audio.newSound(Gdx.files.internal("lose.ogg"));
		font1 = new BitmapFont();
		font1.setColor(Color.BLACK);
		font1.getData().setScale(9);





        sr = new ShapeRenderer();
		c_bird = new Circle();
		c_ari1 = new Circle[arisayisi];

		c_ari2 = new Circle[arisayisi];

		c_ari3 = new Circle[arisayisi];

		d = Gdx.graphics.getWidth()/2;

		for(int i=0;i< arisayisi;i++){
			arilarx[i]=Gdx.graphics.getWidth()+i*d;
			Random r1= new Random();
			Random r2= new Random();
			Random r3= new Random();


			arilary[0][i] = r1.nextFloat() * Gdx.graphics.getHeight();
			arilary[1][i] = r2.nextFloat() * Gdx.graphics.getHeight();
			arilary[2][i] = r3.nextFloat() * Gdx.graphics.getHeight();
			c_ari1[i]=new Circle();
			c_ari2[i]=new Circle();
			c_ari3[i]=new Circle();


		}

	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(img,0,0,sw,sh);
		batch.draw(bird,bx,by,bw,bh);


		if(state==1){
		    flag1=true;

		    if(Gdx.input.justTouched()){
				v=-15;
			}
			for(int i=0;i< arisayisi;i++){
				if(arilarx[i]<bw){
					arilarx[i] += arisayisi*d;
					Random r1= new Random();
					Random r2= new Random();
					Random r3= new Random();


					arilary[0][i] = r1.nextFloat() * Gdx.graphics.getHeight()-bh;
					arilary[1][i] = r2.nextFloat() * Gdx.graphics.getHeight()-bh;
					arilary[2][i] = r3.nextFloat() * Gdx.graphics.getHeight()-bh;

				}
				if(bx>arilarx[i] && flag){
					puan++;
					System.out.println(puan);
					flag=false;
				}
				if(arilarx[i]<bw+4){
					flag=true;
				}



				arilarx[i] -=4;
				batch.draw(ari1,arilarx[i],arilary[0][i],bw,bh);
				batch.draw(ari2,arilarx[i],arilary[1][i],bw,bh);
				batch.draw(ari3,arilarx[i],arilary[2][i],bw,bh);

			}
			if(by<0){
				state = 2;
				by=sh/3;
				v=0;


			}else{
				v = v + gravity;
				by=by-v;
			}

		}
		else if(state==2){
            font1.draw(batch,"Oyun Bitti! Tekrar Denemek için Dokun!",0,Gdx.graphics.getHeight()/2);

            if(flag1){
                sound.play();
                flag1=false;
            }







            puan=0;
            v=0;


            if(Gdx.input.justTouched()){
				state=1;
                for(int i=0;i< arisayisi;i++){
                    arilarx[i]=Gdx.graphics.getWidth()+i*d;
                    Random r1= new Random();
                    Random r2= new Random();
                    Random r3= new Random();


                    arilary[0][i] = r1.nextFloat() * Gdx.graphics.getHeight();
                    arilary[1][i] = r2.nextFloat() * Gdx.graphics.getHeight();
                    arilary[2][i] = r3.nextFloat() * Gdx.graphics.getHeight();
                    c_ari1[i]=new Circle();
                    c_ari2[i]=new Circle();
                    c_ari3[i]=new Circle();


                }


			}
		}
		else if(state == 0){
		    flag1=true;

            font1.draw(batch,"Baslamak için dokun!",Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/2);



            puan=0;
            v=0;
            if(Gdx.input.justTouched()){
                state=1;
            }

        }

		font.draw(batch,String.valueOf(puan),Gdx.graphics.getWidth()-bw,bh);


		c_bird.set(bx+bw/2,by+bh/2,bw/2);
		//sr.begin(ShapeRenderer.ShapeType.Filled);
		//sr.setColor(Color.BLUE);
		//sr.circle(bx+bw/2,by+bh/2,bw/2);
		for (int i = 0 ; i < arisayisi; i++){
			//sr.circle(arilarx[i]+bw/2,arilary[0][i]+bh/2,bw/2);
			//sr.circle(arilarx[i]+bw/2,arilary[1][i]+bh/2,bw/2);
			//sr.circle(arilarx[i]+bw/2,arilary[2][i]+bh/2,bw/2);
			c_ari1[i].set(arilarx[i]+bw/2,arilary[0][i]+bh/2,bw/2);
			c_ari2[i].set(arilarx[i]+bw/2,arilary[1][i]+bh/2,bw/2);
			c_ari3[i].set(arilarx[i]+bw/2,arilary[2][i]+bh/2,bw/2);
			if(Intersector.overlaps(c_bird,c_ari1[i]) || Intersector.overlaps(c_bird,c_ari2[i]) || Intersector.overlaps(c_bird,c_ari3[i])){

			   state=2;
			}
		}

        batch.end();
		//sr.end();

	}
	@Override
	public void dispose () {

	}
}
