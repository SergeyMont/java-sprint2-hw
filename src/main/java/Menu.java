public class Menu {
    static void printMenu() {
        System.out.println("Что вы хотите сделать? ");
        System.out.println("1 - Получить список всех задач");
        System.out.println("2 - Получить список всех эпиков");
        System.out.println("3 - Получить подзадач эпика");
        System.out.println("4 - Получение задачи любого типа по идентификатору.");
        System.out.println("5 - Добавление новой задачи, эпика и подзадачи. Сам объект должен " +
                "передаваться в качестве параметра.");
        System.out.println("6 - Обновление задачи любого типа по идентификатору. Новая версия " +
                "объекта передаётся в виде параметра.");
        System.out.println("7 - Удаление ранее добавленных задач — по идентификатору.");
        System.out.println("8 - Удаление ранее добавленных задач — всех .");
        System.out.println("9 - Получить историю просмотров .");
        System.out.println("10 - Демонстрация загрузки менеджера из файла");
        System.out.println("0 - Выход");
    }
}
