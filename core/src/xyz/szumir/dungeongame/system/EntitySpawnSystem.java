package xyz.szumir.dungeongame.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import xyz.szumir.dungeongame.factory.BodyFactory;
import xyz.szumir.dungeongame.helper.Constants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.IntStream;

public class EntitySpawnSystem {

    private Engine engine;
    private World world;
    private TiledMap map;
    public EntitySpawnSystem(Engine engine, World world, TiledMap map) {
        this.engine = engine;
        this.world = world;
        this.map = map;
        initMap();
    }


    public void initMap() {

        for(MapLayer mapLayer : map.getLayers()) {

            if(mapLayer instanceof TiledMapTileLayer) {
                TiledMapTileLayer tileLayer = (TiledMapTileLayer) mapLayer;
                IntStream.range(0, tileLayer.getWidth()).forEach(x -> {
                    IntStream.range(0, tileLayer.getHeight()).forEach(y -> {
                        TiledMapTileLayer.Cell cell = tileLayer.getCell(x,y);
                        if(cell == null) return;
                        if(cell.getTile().getObjects().getCount() == 0) return;

                        cell.getTile().getObjects().forEach(obj -> {

                            float width = obj.getProperties().get("width", Float.class);
                            float height = obj.getProperties().get("height", Float.class);
                            int xPos = x*16;
                            int yPos = y*16;

                            engine.addEntity(BodyFactory.createSolid(engine,
                                    world,
                                    (width*Constants.SCALE/2)+xPos*Constants.SCALE,
                                    (height*Constants.SCALE/2)+yPos*Constants.SCALE,
                                    width*Constants.SCALE/2,
                                    height*Constants.SCALE/2
                            ));

                        });

                    });
                });
            }
            for(MapObject entity : mapLayer.getObjects()) {
                System.out.println(entity);

                if(entity instanceof TiledMapTileMapObject) {
                    if(!entity.getProperties().containsKey("type")) return;
                    TiledMapTileMapObject rect = (TiledMapTileMapObject) entity;

                    spawnObject(rect);
                }

                if(entity instanceof RectangleMapObject) {
                    RectangleMapObject rectMap = (RectangleMapObject) entity;
                    Rectangle rectangle = rectMap.getRectangle();

                    engine.addEntity(BodyFactory.createSolid(engine,
                            world,
                            (rectangle.getWidth()*Constants.SCALE/2)+rectangle.getX()*Constants.SCALE,
                            (rectangle.getHeight()*Constants.SCALE/2)+rectangle.getY()*Constants.SCALE,
                            rectangle.getWidth()*Constants.SCALE/2,
                            rectangle.getHeight()*Constants.SCALE/2
                    ));
                }
            }
        }

    }

    private void spawnObject(TiledMapTileMapObject mapObject) {
        switch (mapObject.getProperties().get("type", String.class)) {
            case "Chest":
            case "Hammer":
                engine.addEntity(BodyFactory.createObject(engine,
                        world,
                        mapObject.getTextureRegion(),
                        mapObject.getX()*Constants.SCALE,
                        mapObject.getY()*Constants.SCALE,
                        16,
                        16
                ));
                break;
            case "Player":
                engine.addEntity(BodyFactory.createPlayer(engine,
                        world,
                        mapObject.getX()*Constants.SCALE,
                        mapObject.getY()*Constants.SCALE,
                        16,
                        16
                ));
        }
    }

}
