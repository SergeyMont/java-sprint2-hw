public class Menu {
    static void printMenu() {
        String tasks="Эпик, Задача, Подзадача";
        System.out.println("Что вы хотите сделать? ");
        System.out.println("1 - Получить список всех задач");
        System.out.println("2 - Получить список всех эпиков");
        System.out.println("3 - Получить задачу типа: "+tasks);
        System.out.println("4 - Добавление новой задачи типа: "+tasks);
        System.out.println("5 - Обновление задачи типа: "+tasks);
        System.out.println("6 - Удаление ранее добавленных задач");
        System.out.println("0 - Выход");
    }
}
