package com.khorn.terraincontrol.biomelayers.layers;

import com.khorn.terraincontrol.biomelayers.ArraysCache;
import com.khorn.terraincontrol.LocalBiome;

public class LayerBiome extends Layer
{
    public LocalBiome[] biomes;
    public LocalBiome[] ice_biomes;


    public LayerBiome(long paramLong, Layer paramGenLayer)
    {
        super(paramLong);
        this.child = paramGenLayer;
    }

    public int[] GetBiomes(int cacheId, int x, int z, int x_size, int z_size)
    {
        int[] arrayOfInt1 = this.child.GetBiomes(cacheId, x, z, x_size, z_size);

        int[] arrayOfInt2 = ArraysCache.GetArray(cacheId, x_size * z_size);
        for (int i = 0; i < z_size; i++)
        {
            for (int j = 0; j < x_size; j++)
            {
                SetSeed(j + x, i + z);
                int currentPiece = arrayOfInt1[(j + i * x_size)];


                if ((currentPiece & BiomeBits) == 0)    // without biome
                {
                    if (this.biomes.length > 0 && (currentPiece & IceBit) == 0) // Normal Biome
                    {
                        LocalBiome biome = this.biomes[nextInt(this.biomes.length)];
                        if (biome != null)
                            currentPiece = currentPiece | biome.getId();
                    } else if (this.ice_biomes.length > 0 && (currentPiece & IceBit) != 0)
                    {
                        LocalBiome biome = this.ice_biomes[nextInt(this.ice_biomes.length)];
                        if (biome != null)
                            currentPiece = currentPiece | biome.getId();
                    }
                }

                arrayOfInt2[(j + i * x_size)] = currentPiece;


            }
        }

        return arrayOfInt2;
    }
}