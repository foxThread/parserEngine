
**Движок сбора информации с веб ресурсов.**

**Реализован сбор информации в категории "Квартиры" по Авито и сохранение результатов в Excel.**

  

## Особенности реализации:

**Движок.**

1. Сбор происходит по следующей системе:

		- Данные расположены на страничках под номерами

		- Каждая страничка содержит набор ссылок на сущности

		- Собираем ссылки

		- Собираем контент по каждой ссылке

		- Собираем собственно сами объекты, обрабатывая контент

2. Можно собирать любые типы объектов. Для этого необходимо реализовать интерфейс.

		public interface ObjectParser<T> {
			public T getObjectFromString(String content,String url) throws ParseErrorException;
		}


3. Данные сохраняем в хранилище, реализующее интерфейс:

		public interface ObjectsRepository<T> { 

			public void addLink(String url) throws ObjectSavingException;
			public void addContent(SavedPage savedPage) throws ObjectSavingException;
			public void addObject(T object) throws ObjectSavingException;
			public ArrayList<SavedPage> getAllLinks();
			public ArrayList<T> getAllObjects();
			public boolean isLinkExist(String url);
			public boolean isContentLoaded(String url);
			public boolean isObjectParsed(String url);
			public long getObjectsAmount();
			public long initContentCursor();
			public SavedPage getNextContentItem();
		}

4. Данные по Url получаем ,реализуя абстрактный класс:

		public abstract class HtmlGetter {
			protected abstract String getHtmlString(String urlLink);
		}

5. Сам процесс реализован с использованием GUI ( JavaFX)

 **Реализация Авито.**

1. Используем MongoDB для хранения результатов. Chromium CEF для получения контента.

2. Исходные данные в файле .cfg,вида:

		// Номер первой странички
		firstPage=1
		//Номер последней странички
		lastPage=1
		// имя базы, куда сохранять (Используем MongoDB)
		dbName=parser
		// имя коллекции
		taskName=piter
		// Основной URL сайта
		baseUrl=https://www.avito.ru
		//Url критериев поиска
		baseUrlForPages=https://www.avito.ru/sankt-peterburg/kvartiry/prodam/novostroyka-			ASgBAQICAUSSA8YQAUDmBxSOUg?cd=1
		// префикс для номера страницы
		pagePrefix= &p=
		//Город
		cityName=Санкт-Петербург
 
		//Куда сохранять результаты
		pathToExcel=f:\\report.xls  
		//Куда сохранять pdf версии страниц объектов
		pdfSavingPath=f:\\pdf

**3. Пример использования**

		//taskConfig - объект,хранит параметры из пункта 2

		ObjectParser<Flat> parser=new AvitoFlatParser(); // реализация разбора квартиры с Авито с ее странички
		//Реализация хранилища MongoDB (запускается на localhost)
		ObjectsRepository<Flat> repo=new MongoRepository<Flat>taskConfig.dbName,taskConfig.taskName,Flat.class);
		//Реализация получения данных
		HtmlGetter htmlGetter=new ChromiumHtmlGetter(taskConfig.pdfSavingPath);  
		//Формируем поток JavaFx, который потом запускаем из GUI
		task=new MainParsingTask<Flat>(parser,repo,htmlGetter);
		task.setFirstPage(taskConfig.firstPage);
		task.setLastPage(taskConfig.lastPage);
		task.setBaseUrl(taskConfig.baseUrl);
		task.setBaseUrlForPages(taskConfig.baseUrlForPages);
		task.setPagePrefix(taskConfig.pagePrefix);

**4. Компиляция и запуск:**

		!!! Для работы Chromium необходимо скачать Java Chromium Embedded Framework (JCEF) Binary Distribution for Windows 64-bit и прописать в PATH: \bin\lib\win64 !!!

		mvn clean package;

		java -jar target/parser-jar-with-dependencies.jar

		Выбираем CFG файл,запускаем задачу.

  

**5. Вопросы для решения:**

		- Cбор ссылок реализован непосредсвенно для Авито. Чтобы изменить алгоритм их поиска надо править WebPageLinksCollector, в то время как ContentParser ,ObjectsContentCollector можно использовать для любых сайтов.


Программа реализует скорость обхода страничек, сравнимую со скоростью работы человека. Специально делаются паузы между получением отдельных страниц, чтобы никак не нагружать инфрастуктуру сетевых серверов. Не получаются никакие персональные данные.  Данный проект создавался  исключительно в целях саморазвития. 
