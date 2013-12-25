package LDA.core.algorithm.lda;

import javax.sound.sampled.Line;


public class LDA implements Runnable{

	@Override
	public void run() {
		LDAOption option = new LDAOption();
		
		option.dir = "F:/LDA/result";
		option.dfile = "weibo.dat";
		option.est = true;      //Specify whether we want to continue the last estimation
		option.estc = false;    //Specify whether we want to do inference
		option.inf = false;    //�Ƿ�ʹ����ǰ�Ѿ�ѵ���õ�ģ�ͽ����ƶ�
		option.modelName = "model-final";
		option.niters = 3000;
		
		Estimator estimator = new Estimator();
		estimator.init(option);
		estimator.estimate();
	}
	public void estimate(){
		LDAOption ldaOption = new LDAOption();
		ldaOption.estc = true;
		ldaOption.inf =true; 
		ldaOption.dir ="F:/LDA/result"; 
		ldaOption.modelName= "model-final";
		ldaOption.niters =1000;
		
		Inferencer inferencer = new Inferencer(); 
		inferencer.init(ldaOption);
		ldaOption.dfile= "������.dat";	
		Model newModel =inferencer.inference();
	}

	public static void main(String[] args) {
		new LDA().run();
		//new LDA().estimate();
	}
}
