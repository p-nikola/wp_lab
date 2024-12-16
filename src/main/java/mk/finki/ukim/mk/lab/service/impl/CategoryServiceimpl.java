package mk.finki.ukim.mk.lab.service.impl;

import mk.finki.ukim.mk.lab.model.Category;
import mk.finki.ukim.mk.lab.repository.jpa.CategoryRepositroyJPA;
import mk.finki.ukim.mk.lab.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceimpl  implements CategoryService {

    private final CategoryRepositroyJPA categoryRepositroyJPA;

    public CategoryServiceimpl(CategoryRepositroyJPA categoryRepositroyJPA) {
        this.categoryRepositroyJPA = categoryRepositroyJPA;
    }

    @Override
    public List<Category> listAll() {
        return categoryRepositroyJPA.findAll();
    }
}
