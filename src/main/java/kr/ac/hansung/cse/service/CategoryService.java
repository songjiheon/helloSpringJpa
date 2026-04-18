package kr.ac.hansung.cse.service;

import kr.ac.hansung.cse.exception.DuplicateCategoryException;
import kr.ac.hansung.cse.model.Category;
import kr.ac.hansung.cse.model.Product;
import kr.ac.hansung.cse.repository.CategoryRepository;
import kr.ac.hansung.cse.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//카테고리의 로직 처리 서비스

@Service
@Transactional(readOnly = true)
public class CategoryService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    //생성자 주입
    public CategoryService(ProductRepository productRepository,
                            CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    //카테고리 생성 : 사용자 입력 -> 중복 검사 -> 중복이라면 예외 발생, 아니라면 새 Category 저장 후 반환

    @Transactional
    public Category createCategory(String name) {
        // 중복 검사: 이름이 이미 있으면 예외 발생
        categoryRepository.findByName(name)
                .ifPresent(c -> { throw new DuplicateCategoryException(name); });
        return categoryRepository.save(new Category(name));
    }

    //카테고리 삭제 : 사용자 삭제 버튼 -> 연결된 상품 수 조회 -> 1개 이상일 시 예외 발생, 아니라면 카테고리 삭제

    @Transactional
    public void deleteCategory(Long id){
        //카테고리와 연결된 상품이 있으면 예외 발생
        long count=categoryRepository.countProductsByCategoryId(id);
        if(count > 0) throw new IllegalStateException(
                "상품 " + count + "개가 연결되어 있어 삭제할 수 없습니다."
        );
        categoryRepository.delete(id);
    }

    //전체 카테고리 조회
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

}

