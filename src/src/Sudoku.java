package src;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by hongzhiwen on 03/06/2017.
 * SudokuHelper 是数独游戏的帮助类，里面提供数据结构与算法
 * 与界面的实现是相分离的，满足 MVC 设计思想
 */

public class Sudoku {
	public class Record{
		/**
		 * org表示初始值，fin表示结束值
		 */
		public int i,j,org,fin;
		public Record(int i,int j,int num1,int num2){
			this.i=i;
			this.j=j;
			this.org=num1;
			this.fin=num2;
		}
	}

	private int[][] sudokuData=new int[9][9];
	private int[][] theResult=new int[9][9];
	private int[][] theInit=new int[9][9];
	private LinkedList<Record> History=new LinkedList<Record>();
	private boolean resultComplete=true;
  	//三个设置分别为：判错辅助，视觉辅助，逻辑辅助
	public static int[] settings={1,1,1};
	public int blocks;
  	
  	//生成终盘
  	private void initTheResult() {
  		int[] canPutSum = new int[9];
  		int[] used = new int[9];
  		// 初始化地图数组中没有填入任何数字
  		for (int i=0; i<9; i++) {
  			for (int j=0; j<9; j++) {
  				theResult[i][j]=-1;
  			}
  		}

  		// 依次填入1~9
  		for (int num=1; num<=9; num++) {
  			for (int i=0; i<9; i++) {
  				used[i]=-1;
  				canPutSum[i]=-1;
  			}
  			// 遍历大九宫格中的每个小九宫格
  			for (int i=0; i<9; i++) {
  				if (canPutSum[i]==-1) {
  					canPutSum[i]=getCanPutSum(i,num);
  				}
  				if (canPutSum[i]==1) {
  					used[i]=-1;
  				}
  				
  				if (canPutSum[i]==0) {
  					canPutSum[i]=-1;
  					used[i] = -1;
  					// 如果当前小九宫格中不能放入数字num，则回到前一个小九宫格
  					if (i>0) {
  						// 将前一个九宫格中放num的位置清空
  						if (used[i-1]!=-1) {
  							//theResult[(int) (Math.floor(used[i-1]/3)+Math.floor((i-1)/3)*3)][used[i-1]%3+((i-1)%3)*3]=-1;
  							clearNum(i-1,num);
  						}
  						i-=2;
  						continue;
  					}else{
  						resultComplete=false;
  						return;
  					}
  				}else{
  					// 将num放入当前小九宫格中
  					boolean flag=false;
  					while (!flag){
  						int j=(int)(Math.random()*9);
  						int ii=(i/3)*3+j/3;
  						int jj=(i%3)*3+j%3;
  						if (theResult[ii][jj]==-1 && j!=used[i] && isCanPut(ii, jj, num)) {
  							theResult[ii][jj]=num;
  							used[i]=j;
  							canPutSum[i]-=1;
  							flag=true;
  						}
  					}
  				}
  			}
  		}
  	}
 
	//把一个小九宫中的num删除
	private void clearNum(int i,int num) {
		for (int j=0; j<9; j++) {
			int ii=(i/3)*3+j/3;
			int jj=(i%3)*3+j%3;
			if (theResult[ii][jj]==num) {
				theResult[ii][jj]=-1;
			}
		}
	}

	//得到当前小九宫格可以放入数字num的位置数目
	private int getCanPutSum(int i,int num) {
		int sum=0;
		for (int j=0; j<9; j++) {
			int ii=(i/3)*3+j/3;
			int jj=i%3*3+j%3;
			if (theResult[ii][jj]==-1 && isCanPut(ii, jj, num)) {
				++sum;
			}
		}
		return sum;
	}

	//指定横纵坐标点是否可以放置num
	private boolean isCanPut(int ii,int jj,int num) {
		// 判断指定坐标点的同行或同列是否有相同数字，要是有则为false
		for (int i=0; i<9; i++) {
			if (theResult[ii][i]==num) {
				return false;
			}
			if (theResult[i][jj]==num) {
				return false;
			}
		}
		return true;
	}

	//基于最小候选数的深度优先搜索，寻找当前挖空法是否存在解
	private int[][] dfs(int[][] theSudoku){
		int[] temp; //寻找一个可选数最少的的点
		int locX=-1,locY=-1,sum=0,minsum=10;
		int[][] _sudoku=new int[9][9];
		for (int i=0;i<9;i++)
		{
			for (int j=0;j<9;j++)
			{
				_sudoku[i][j]=theSudoku[i][j];
			}
		}

		for (int i=0;i<9;i++)
		{
			for (int j=0; j<9; j++)
			{
				if (_sudoku[i][j]!=0) continue;
				temp=new int[10];
				sum=9;
				for (int k=0; k<9; k++)
				{
					temp[_sudoku[i][k]]=1;
					temp[_sudoku[k][j]]=1;
					temp[_sudoku[(i/3)*3+(k/3)][(j/3)*3+(k%3)]]=1;
				}
				for (int k=1; k<10; k++) sum-=temp[k];
				if (sum<minsum)
				{
					if (sum==0) //该方格无解，弹出
					{
						_sudoku[0][0]=-1;
						return _sudoku;
					}
					minsum=sum;
					locX=i;
					locY=j;
				}
			}
		}
		
		if (minsum==10) //sudoku已经被全部填满
		{
			theResult=_sudoku;
			return(_sudoku);
		}
		
		temp=new int[10]; //获取最小可选数方格内可选的数集
		for (int k=0; k<9; k++)
		{
			temp[_sudoku[locX][k]]=1;
			temp[_sudoku[k][locY]]=1;
			temp[_sudoku[(locX/3)*3+(k/3)][(locY/3)*3+(k%3)]]=1;
		}
		
		int solution=0; //开始递归调用
		int[][] result=new int[9][9];
		int[][] finalResult=new int[9][9];
		for (int k=1; k<10; k++)
		{
			if (temp[k]==0)
			{
				_sudoku[locX][locY]=k;
				result=dfs(_sudoku);
				finalResult=result;
				if (result[0][0]>0){
					solution++;
					if (solution>1) //多解，弹出
					{
						_sudoku[0][0]=-1;
						return _sudoku;
					}
				}
			}
		}
		return finalResult;
	}

    /*
     * 给定空格数生成一个数独
     */
	public Sudoku(int blankSum) 
	{
		this.blocks=blankSum;
		int[][] tempSudoku=new int[9][9];
		boolean[][] cutOut=new boolean[9][9];
		boolean[][] tried;
		int temp;
		int[][] tempResult=new int[9][9];
		int randi,randj,triedNum,cutNum;
		
		do{
			resultComplete = true;
			initTheResult();
		}while(!resultComplete);

		for (int i=0; i<9; i++)
		{
			for (int j=0; j<9; j++)
			{
				tempSudoku[i][j]=theResult[i][j];
			}
		}
		
		cutNum=0;
		for (int i=0; i<blankSum; i++)
		{
			triedNum=0;
			tried=new boolean[9][9];
			do
			{
				do
				{
					randi=(int)(Math.random()*9);
					randj=(int)(Math.random()*9);
				}
				while (tried[randi][randj]==true || cutOut[randi][randj]==true);
				tried[randi][randj]=true;
				triedNum++;
				if (triedNum+cutNum==81) {i=blankSum;break;}
				temp=theResult[randi][randj];
				tempSudoku[randi][randj]=0;
				tempResult=dfs(tempSudoku);
				tempSudoku[randi][randj]=temp;
			}
			while (tempResult[0][0]==-1);
			tempSudoku[randi][randj]=0;
			cutOut[randi][randj]=true;
			cutNum++;
		}
		sudokuData=tempSudoku;
		
		// 通过序列化实现深拷贝 
        try{
        	ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
            ObjectOutputStream oos = new ObjectOutputStream(bos); 
            // 序列化以及传递这个对象 
            oos.writeObject(tempSudoku); 
            oos.flush(); 
            ByteArrayInputStream bin = new ByteArrayInputStream(bos.toByteArray()); 
            ObjectInputStream ois = new ObjectInputStream(bin); 
            // 返回新的对象 
            theInit = (int[][]) ois.readObject(); 
        }catch(Exception e){}
        
	}

	/*
	 * 判断该数独是否具有历史记录
	 */
	public boolean hasHistory(){
		if (History.size()>0) return true; else return false;
	}
	
    /*
     * 得到当前地图数组
     */
	public int[][] getMap(){
		return sudokuData;
	}
	
	/*
	 * 写入一个数字
	 */
	public void insert(int i, int j, int num){
		Record record=new Record(i,j,sudokuData[i][j],num);
		History.add(record);
		sudokuData[i][j]=0-num;
	}
	
    /*
     * 得到终盘地图数组
     */
	public int[][] getResult(){
		return theResult;
	}

	/*
	 * 判断某个格子是否满足数独规则
	 */
    public boolean check(int i, int j) {
        if (sudokuData[i][j]==0) {
            return false;
        }
        for (int k = 0; k < 9; k++) {
            if (Math.abs(sudokuData[i][j])==Math.abs(sudokuData[i][k]) && j != k) {
                return false;
            }
            if (Math.abs(sudokuData[i][j])==Math.abs(sudokuData[k][j]) && i != k) {
                return false;
            }
            int ii = (i / 3) * 3 + k / 3;
            int jj = (j / 3) * 3 + k % 3;
            if (Math.abs(sudokuData[i][j])==Math.abs(sudokuData[ii][jj]) && !(i == ii && j == jj)) {
                return false;
            }
        }
        return true;
    }
    
    /*
     * 判断数独是否已填完
     */
    public boolean checkFinish() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!check(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /*
     * 判断i,j处能否填入n
     */
    public boolean checkNum(int i, int j, int num){
    	if (sudokuData[i][j]!=0){
    		return false;
    	}
    	for (int x=0; x<9; x++){
    		if (x!=i && Math.abs(sudokuData[x][j])==num){
    			return false;
    		}
    		if (x!=j && Math.abs(sudokuData[i][x])==num){
    			return false;
    		}
    		if ((!(x%3+i/3*3==i && x%3+j/3*3==j)) && Math.abs(sudokuData[x%3+i/3*3][x%3+j/3*3])==num){
    			return false;
    		}
    	}
    	return true;
    }
    
    /*
     * 撤销操作
     */
    public void cancel(){
    	Record record=History.pop();
    	sudokuData[record.i][record.j]=record.org;
    }
    
    /*
     * 重新开始
     */
    public void clear(){
    	History.clear();
		// 通过序列化实现深拷贝 
        try{
        	ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
            ObjectOutputStream oos = new ObjectOutputStream(bos); 
            // 序列化以及传递这个对象 
            oos.writeObject(theInit); 
            oos.flush(); 
            ByteArrayInputStream bin = new ByteArrayInputStream(bos.toByteArray()); 
            ObjectInputStream ois = new ObjectInputStream(bin); 
            // 返回新的对象 
            sudokuData = (int[][]) ois.readObject(); 
        }catch(Exception e){}
    }
}
