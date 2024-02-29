package helpers;

import api.specs.Specifications;
import models.TestEntity;

import java.util.List;

import static configProvider.ConfigProvider.URL;
import static io.restassured.RestAssured.given;

/**
 * Класс для обращения к методам API.
 * @autor Игорь Герасименко
 * @version 1.0
 */
public class ApiHelper {

    /**
     * Метод получения сущности
     * @param id - id сущности
     * @return объект сущности
     */
    public static TestEntity getEntity(int id) {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        TestEntity entity = given()
                .when()
                .get("get/" + id)
                .then()
                .extract().as(TestEntity.class);
        return entity;
    }

    /**
     * Метод получения сущности, которая была удалена
     * @param id - id сущности
     * @return тело ответа сервера
     */
    public static String getDeletedEntity(int id) {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecServerError500());
        String response = given()
                .when()
                .get("get/" + id)
                .then()
                .extract().body().asString();
        return response;
    }

    /**
     * Метод создания сущности
     * @param entity - инстанс сущности
     * @return id созданной сущности
     */
    public static int createEntity(TestEntity entity) {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        String id = given()
                .when()
                .body(entity)
                .post("create")
                .then()
                .extract().asString();
        return Integer.parseInt(id);
    }

    /**
     * Метод обновления сущности
     * @param entity - инстанс сущности
     * @param entityId - id сущности
     */
    public static void patchEntity(TestEntity entity, int entityId) {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK204());
        given()
                .when()
                .body(entity)
                .patch("patch/" + entityId)
                .then();
    }

    /**
     * Метод удаления сущности
     * @param entityId - id сущности
     */
    public static void deleteEntity(int entityId) {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK204());
        given()
                .when()
                .delete("delete/" + entityId)
                .then();
    }

    /**
     * Метод получения списка сущностей
     * @return список сущностей сущности
     */
    public static List<TestEntity> getEntityList() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        List<TestEntity> testEntities = given()
                .when()
                .get("getAll")
                .then()
                .extract().body().jsonPath().getList("entity", TestEntity.class);
        return testEntities;
    }


}
