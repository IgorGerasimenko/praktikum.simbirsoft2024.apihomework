package api;

import helpers.ApiHelper;
import models.TestEntity;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static io.qameta.allure.Allure.step;

/**
 * Класс тестов API методов сервиса управления сущностями
 *
 * @version 1.0
 * @autor Игорь Герасименко
 */
//@Execution(ExecutionMode.CONCURRENT)
public class EntityTest {
    /**
     * Список id сущностей, которые были созданы во время тестирования
     * и которые будут удалены
     */
    static List<Integer> entityToDeleteIdList = new ArrayList<>();

    @Test
    @DisplayName("Успешное создание сущности")
    public void createEntity() {
        TestEntity expEntity = step("Создать объект сущности", () ->
                TestEntity.builder().build()
        );
        int entityId = step("Вызов метода create - Создаем в БД Сущность", () ->
                ApiHelper.createEntity(expEntity)
        );
        entityToDeleteIdList.add(entityId); //Добавление id сущности в список на удаление
        TestEntity actEntity = step("Получаем созданную сущность из БД", () ->
                ApiHelper.getEntity(entityId)
        );
        step("Проверка, что переданная в API и созданная в БД сущность одинаковы", () ->
                Assertions.assertEquals(expEntity, actEntity)
        );
    }

    @Test
    @DisplayName("Удалить сущность")
    public void deleteEntity() {
        TestEntity expEntity = step("Создать объект сущности", () ->
                TestEntity.builder().build()
        );
        int entityId = step("Вызов метода create - Создаем в БД Сущность", () ->
                ApiHelper.createEntity(expEntity)
        );
        step("Вызов метода удаления созданной сущности", () ->
                ApiHelper.deleteEntity(entityId)
        );
        String expResponse = "{\"error\":\"no rows in result set\"}";
        String actResponse = ApiHelper.getDeletedEntity(entityId);
        step("Проверка, что при запросе удаленной сущности получаем error", () ->
                Assertions.assertEquals(expResponse, actResponse)
        );
    }

    @Test
    @DisplayName("Получить сущность")
    public void getEntity() {
        TestEntity expEntity = step("Создать объект сущности", () ->
                TestEntity.builder().build()
        );
        int entityId = step("Вызов метода create - Создаем в БД Сущность", () ->
                ApiHelper.createEntity(expEntity)
        );
        entityToDeleteIdList.add(entityId); //Добавление id сущности в список на удаление
        TestEntity actEntity = step("Получаем созданную сущность", () ->
                ApiHelper.getEntity(entityId)
        );
        step("Проверка, что переданная в API и созданная в БД сущность одинаковы", () ->
                Assertions.assertEquals(expEntity, actEntity)
        );
    }

    @Test
    @DisplayName("Обновление сущности и ее дополнений")
    public void patch() {
        TestEntity expEntity = step("Создать объект сущности", () ->
                TestEntity.builder().build()
        );
        int entityId = step("Вызов метода create - Создаем в БД Сущность", () ->
                ApiHelper.createEntity(expEntity)
        );
        entityToDeleteIdList.add(entityId); //Добавление id сущности в список на удаление
        TestEntity patchEntity = step("Создать объект измененной сущности", () ->
                TestEntity.builder()
                        .verified(false)
                        .build()
        );
        step("Отправка запроса на обновление сущности", () ->
                ApiHelper.patchEntity(patchEntity, entityId)
        );
        TestEntity actEntity = step("Получаем обновленную сущность из БД", () ->
                ApiHelper.getEntity(entityId)
        );
        step("Проверка, что переданная в API изменненная и созданная в БД сущность одинаковы", () ->
                Assertions.assertEquals(patchEntity, actEntity)
        );
    }

    @Test
    @DisplayName("Список сущностей")
    public void entityList() {
        TestEntity expEntity = step("Создать объект сущности", () ->
                TestEntity.builder().build()
        );
        List<TestEntity> entityList = step("Получаем список сущностей из БД и записываем его в переменную", () ->
                ApiHelper.getEntityList()
        );
        List<TestEntity> expEntityList = step("Создаем ожидаемый список сущностей", () ->
                new ArrayList<>()
        );
        step("Добавляем в ожидаемый список сущностей созданную сущность", () -> {
            expEntityList.addAll(entityList);
            expEntityList.add(expEntity);
        });
        int entityId = step("Создаем сущность в БД", () ->
                ApiHelper.createEntity(expEntity)
        );
        entityToDeleteIdList.add(entityId); //Добавление id сущности в список на удаление
        List<TestEntity> actEntityList = step("Получаем фактический список сущностей", () ->
                ApiHelper.getEntityList()
        );
        step("Проверка, что ожидаемый и фактический список сущностей равны", () ->
                Assertions.assertEquals(expEntityList, actEntityList)
        );
    }

    @AfterAll
    public static void deleteTest() {
        for (Integer id : entityToDeleteIdList) {
            ApiHelper.deleteEntity(id);
            System.out.println("Удалена тестовая сущность с id:" + id);
        }
    }
}







