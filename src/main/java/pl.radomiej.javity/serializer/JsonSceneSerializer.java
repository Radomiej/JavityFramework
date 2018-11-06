/*
 * Copyright (c) 2018 di support GmbH
 */

package pl.radomiej.javity.serializer;

import com.badlogic.gdx.utils.*;
import pl.radomiej.javity.JGameObject;
import pl.radomiej.javity.components.Transform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class JsonSceneSerializer {
    public static Json json;
    static Json sceneReadJson;
    private static HashMap<UUID, JGameObject> loadSceneObjects = new HashMap<UUID, JGameObject>();
    static {
        json = new Json();
        sceneReadJson = new Json();
        prepareJsonParser(json);
        prepareJsonParser(sceneReadJson);
    }

    private static void prepareJsonParser(Json jsonToPrepare) {
        jsonToPrepare.setOutputType(JsonWriter.OutputType.javascript);
        jsonToPrepare.setUsePrototypes(false);
        jsonToPrepare.addClassTag("Transform", Transform.class);
        jsonToPrepare.setSerializer(OrderedMap.class, new Json.Serializer<OrderedMap>() {
            @Override
            public void write(Json json, OrderedMap object, Class knownType) {
                ArrayList<Object> keysRaw = new ArrayList<Object>();
                ArrayList<Object> valuesRaw = new ArrayList<Object>();
                ObjectMap.Keys keys = object.keys();
                for (Object key : keys.iterator()) {
                    Object value = object.get(key);
                    keysRaw.add(key);
                    valuesRaw.add(value);
                }

                RawMap rawMap = new RawMap();
                rawMap.keysRaw = keysRaw;
                rawMap.valuesRaw = valuesRaw;

                json.writeObjectStart();
                json.writeValue("raw", rawMap);
                json.writeObjectEnd();
            }

            @Override
            public OrderedMap read(Json json, JsonValue jsonData, Class type) {
                RawMap rawMap = json.readValue(RawMap.class, jsonData.child());

                OrderedMap map = new OrderedMap();
                try {
                    map = (OrderedMap) type.newInstance();
                } catch (InstantiationException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                for (int x = 0; x < rawMap.keysRaw.size(); x++) {
                    map.put(rawMap.keysRaw.get(x), rawMap.valuesRaw.get(x));
                }

                return map;
            }
        });
    }
}
