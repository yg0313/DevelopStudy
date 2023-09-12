package study.datajpa.repository

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import study.datajpa.entity.Item

@SpringBootTest
class ItemRepositoryTest {

    @Autowired
    lateinit var itemRepository: ItemRepository
    @Test
    fun save(){
        val item = Item("A")
        itemRepository.save(item)
    }
}