package com.mockcompany.webapp.service;

import com.mockcompany.webapp.data.ProductItemRepository;
import com.mockcompany.webapp.model.ProductItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {

    private final ProductItemRepository productItemRepository;
    @Autowired
    public SearchService(ProductItemRepository productItemRepository) {
        this.productItemRepository = productItemRepository;
    }

    public List<ProductItem> searchAll(String query) {
        /*
         * TODO: !!!! Implement this method !!!!
         *  The easiest implementation will be to use the findAll as we are below. Then filter using Java
         *  string methods such as contains(...), toLowerCase(...), equals(...), etc.
         *
         *  The requirements are defined in src/test/groovy/com/mockcompany/webapp/controller/SearchControllerSpec.groovy
         *
         *  Read through the tests to get an idea of how search should work.  When the tests are written before the code,
         *  it is known as Test Driven Development (TDD) and is a common best practice. The Spock framework is a great
         *  framework for TDD because the tests are written very descriptively using sentences.
         *
         *    https://spockframework.org/spock/docs/2.0/spock_primer.html
         *
         *  For an added challenge, update the ProductItemRepository to do the filtering at the database layer :)
         */

        Iterable<ProductItem> allItems = this.productItemRepository.findAll();
        List<ProductItem> itemList = new ArrayList<>();

        // This is a loop that the code inside will execute on each of the items from the database.
        for (ProductItem item : allItems) {
            // TODO: Figure out if the item should be returned based on the query parameter!
            boolean matchesSearch = false;
            // Check if the item's name or description contains the query (case-insensitive)
            if (item.getName().toLowerCase().contains(query.toLowerCase()) ||
                    item.getDescription().toLowerCase().contains(query.toLowerCase())) {
                matchesSearch = true;
            }

            // Check for exact match on name or description when quotes are used
            if (query.startsWith("\"") && query.endsWith("\"")) {
                String exactQuery = query.substring(1, query.length() - 1).toLowerCase();
                if (item.getName().toLowerCase().equals(exactQuery) ||
                        item.getDescription().toLowerCase().equals(exactQuery)) {
                    matchesSearch = true;
                }
            }

            if (matchesSearch) {
                itemList.add(item);
            }
        }

        return itemList;
    }

}
