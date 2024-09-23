package org.example.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class CollectionContainer {

    private List<Object>[] collections;

    @SuppressWarnings("unchecked")
    public CollectionContainer(int size) {
        collections = new List[size];
        for (int i = 0; i < size; i++) {
            collections[i] = new ArrayList<>();
        }
    }

    public void addElementToCollection(int index, Object element) {
        if (index < 0 || index >= collections.length) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        collections[index].add(element);
    }

    public List<Object> getCollection(int index) {
        if (index < 0 || index >= collections.length) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        return collections[index];
    }

    public List<Object>[] getCollections() {
        return collections;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("CollectionContainer{\n");
        for (int i = 0; i < collections.length; i++) {
            sb.append("  Collection ").append(i).append(": ").append(collections[i]).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }
}
