package blockchain;
import java.util.ArrayList;
import java.util.*;
import com.google.gson.GsonBuilder;

public class BlockChain {
	
	public static ArrayList<Block> blockchain = new ArrayList<Block>();
	public static int difficulty = 5;

	public static void main(String[] args) {
		String str=null;
		Scanner sc=new Scanner(System.in);  
	        System.out.println("Enter choice \n1.Add a new block \n2.Check validity of blockchain \n3.Get data in JSON format \n4.Get blockchain size \n5.Search the data \n6.Exit");  
	        int ch=sc.nextInt();  
	        while (ch<7)
	        {
	        switch(ch)  
	        {  
	        case 1:  
	        	System.out.println("Enter the data");  
	    		Scanner in = new Scanner(System.in); 
	    		String data = in.nextLine();
	    		if(blockchain.size()>0)
	    		{
	    			addBlock(new Block(data,blockchain.get(blockchain.size()-1).hash));
	    		}
	    		else
	    		{
	    			addBlock(new Block(data, "0"));
	    		}

	            break;  
	              
	        case 2:  
	        	System.out.println("\nBlockchain is Valid: " + isChainValid()); 
	            break;  
	  
	        case 3:  

	    		String blockchainJson = StringUtil.getJson(blockchain);
	    		System.out.println("\nThe block chain: ");
	    		System.out.println(blockchainJson);
	            break;  
	  
	        case 4:  
	        	System.out.println("Blockchain size: "+blockchain.size());
	            break;  
	        case 5:  
	        	if(blockchain.size()==0){
				System.out.println("Blockchain is empty");
			}
			else {
				
	        	System.out.println("Enter the data to be searched");
	        	str=sc.next();
	        	search(str);
	        	break;  
			}
	        case 6:  
	        	System.exit(0);
	            break;  
	  
	        default:  
	            System.out.println("Invalid choice");  
	        }
	        System.out.println("\n");
	        System.out.println("Enter choice \n1.Add a new block \n2.Check validity of blockchain \n3.Get data in JSON format \n4.Get blockchain size \n5.Search the data \n6.Exit");
	        ch = sc.nextInt();
	   }	
        }
	public static void search(String str) {
		for (int i=0; i<blockchain.size();i++) {
			if(blockchain.get(i).getData().equals(str))
			{
				System.out.println("Data: "+blockchain.get(i).getData());
				System.out.println("Hash Value: "+blockchain.get(i).getHash());
				System.out.println("Previous Hash: "+blockchain.get(i).getPreviousHash());
				System.out.println("TimeStamp: "+blockchain.get(i).getTimeStamp());
				System.out.println("Nonce: "+blockchain.get(i).getNonce());
				
			}
			else {
				System.out.println("Invalid Search");
			}
		}
			
				
		}
	
	public static Boolean isChainValid() {
		Block currentBlock; 
		Block previousBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');
		
		//loop through blockchain to check hashes:
		for(int i=1; i < blockchain.size(); i++) {
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i-1);
			//compare registered hash and calculated hash:
			if(!currentBlock.hash.equals(currentBlock.calculateHash()) ){
				System.out.println("Current Hashes not equal");			
				return false;
			}
			//compare previous hash and registered previous hash
			if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
				System.out.println("Previous Hashes not equal");
				return false;
			}
			//check if hash is solved
			if(!currentBlock.hash.substring( 0, difficulty).equals(hashTarget)) {
				System.out.println("This block hasn't been mined");
				return false;
			}
			
		}
		return true;
	}
	
	public static void addBlock(Block newBlock) {
		newBlock.mineBlock(difficulty);
		blockchain.add(newBlock);
	}
}
