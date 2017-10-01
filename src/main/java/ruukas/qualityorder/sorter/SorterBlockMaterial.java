package ruukas.qualityorder.sorter;

import java.util.Comparator;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBlockSpecial;
import net.minecraft.item.ItemStack;

public class SorterBlockMaterial extends Sorter implements Comparator<ItemStack>{
	@Override
	public int compare(ItemStack stackNeg, ItemStack stackPos) {
		if(stackNeg.getItem() instanceof ItemBlock || stackNeg.getItem() instanceof ItemBlockSpecial){
			if(stackPos.getItem() instanceof ItemBlock || stackPos.getItem() instanceof ItemBlockSpecial){
				Material matNeg = ((Block)Block.getBlockFromItem(stackNeg.getItem())).getBlockState().getBaseState().getMaterial();
				Material matPos = ((Block)Block.getBlockFromItem(stackPos.getItem())).getBlockState().getBaseState().getMaterial();
				
				if(matNeg == null){
					if(matPos == null){
						return 0;
					}
					else return 1;
				}else if(matPos == null){
					return -1;
				}
				
				if(matNeg == Material.BARRIER){
					if(matPos == Material.BARRIER){
						return 0;
					}else return -1;
				}else if(matPos == Material.BARRIER){
					return 1;
				}
				
				if(matNeg == Material.ANVIL){
					if(matPos == Material.ANVIL){
						return 0;
					}else return -1;
				}else if(matPos == Material.ANVIL){
					return 1;
				}
				
				if(matNeg == Material.CARPET){
					if(matPos == Material.CARPET){
						return 0;
					}else return -1;
				}else if(matPos == Material.CARPET){
					return 1;
				}
				
				if(matNeg == Material.WOOD){
					if(matPos == Material.WOOD){
						return 0;
					}else return -1;
				}else if(matPos == Material.WOOD){
					return 1;
				}
				
				if(matNeg == Material.ROCK){
					if(matPos == Material.ROCK){
						return 0;
					}else return -1;
				}else if(matPos == Material.ROCK){
					return 1;
				}
				
				if(matNeg == Material.CLAY){
					if(matPos == Material.CLAY){
						return 0;
					}else return -1;
				}else if(matPos == Material.CLAY){
					return 1;
				}
				
				if(matNeg == Material.GLASS){
					if(matPos == Material.GLASS){
						return 0;
					}else return -1;
				}else if(matPos == Material.GLASS){
					return 1;
				}
				
				if(matNeg == Material.CARPET){
					if(matPos == Material.CARPET){
						return 0;
					}else return -1;
				}else if(matPos == Material.CARPET){
					return 1;
				}
				
				if(matNeg == Material.CLOTH){
					if(matPos == Material.CLOTH){
						return 0;
					}else return -1;
				}else if(matPos == Material.CLOTH){
					return 1;
				}
								
				//TODO Combine the two kinds of snow
				
				if(matNeg == Material.ICE){
					if(matPos == Material.ICE){
						return 0;
					}else return -1;
				}else if(matPos == Material.ICE){
					return 1;
				}
				
				if(matNeg == Material.PACKED_ICE){
					if(matPos == Material.PACKED_ICE){
						return 0;
					}else return -1;
				}else if(matPos == Material.PACKED_ICE){
					return 1;
				}
				
				if(matNeg == Material.CRAFTED_SNOW){
					if(matPos == Material.CRAFTED_SNOW){
						return 0;
					}else return -1;
				}else if(matPos == Material.CRAFTED_SNOW){
					return 1;
				}
				
				if(matNeg == Material.SNOW){
					if(matPos == Material.SNOW){
						return 0;
					}else return -1;
				}else if(matPos == Material.SNOW){
					return 1;
				}
				
				//TODO combine plants and cactus with &&
				
				if(matNeg == Material.CACTUS){
					if(matPos == Material.CACTUS){
						return 0;
					}else return -1;
				}else if(matPos == Material.CACTUS){
					return 1;
				}
				
				if(matNeg == Material.PLANTS){
					if(matPos == Material.PLANTS){
						return 0;
					}else return -1;
				}else if(matPos == Material.PLANTS){
					return 1;
				}
				
				if(matNeg == Material.GOURD){
					if(matPos == Material.GOURD){
						return 0;
					}else return -1;
				}else if(matPos == Material.GOURD){
					return 1;
				}
				
				if(matNeg == Material.GRASS){
					if(matPos == Material.GRASS){
						return 0;
					}else return -1;
				}else if(matPos == Material.GRASS){
					return 1;
				}
				
				if(matNeg == Material.GROUND){
					if(matPos == Material.GROUND){
						return 0;
					}else return -1;
				}else if(matPos == Material.GROUND){
					return 1;
				}
				
				if(matNeg == Material.SAND){
					if(matPos == Material.SAND){
						return 0;
					}else return -1;
				}else if(matPos == Material.SAND){
					return 1;
				}
				
				if(matNeg == Material.IRON){
					if(matPos == Material.IRON){
						return 0;
					}else return -1;
				}else if(matPos == Material.IRON){
					return 1;
				}
				
				if(matNeg == Material.CORAL){
					if(matPos == Material.CORAL){
						return 0;
					}else return -1;
				}else if(matPos == Material.CORAL){
					return 1;
				}	
				
				if(matNeg == Material.SPONGE){
					if(matPos == Material.SPONGE){
						return 0;
					}else return -1;
				}else if(matPos == Material.SPONGE){
					return 1;
				}	
			}else{
				return -1;
			}
			
		}else if(stackPos.getItem() instanceof ItemBlock || stackPos.getItem() instanceof ItemBlockSpecial){
			return 1;
		}
		
		return 0;
	}
}