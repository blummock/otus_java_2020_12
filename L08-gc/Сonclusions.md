### Garbage Collectors Java

| Статистика              | G1 Xmx512m | G1 Xmx2048m | Serial Collector Xmx2048m | Parallel Collector Xmx2048m | ZGC Xmx2048m |
|:----------------------- |:----------:|:-----------:|:-------------------------:|:---------------------------:|:------------:|
| Работа программы        |  4,397 min |   2,968 min |                 1,131 min |                   1,728 min |    1,686 min |
| Количество сборок       |       8715 |        1033 |                       499 |                         796 |          419 | 
| Затрачено на все сборки |  2,884 min |   1,436 min |                 0,018 min |                   0,657 min |    1,430 min |
| Среднее время сборки    |  19,854 ms |   83,381 ms |                  2,140 ms |                   49,552 ms |   204,711 ms |

### Вывод
В конкретном случае для данной программы и моего железа лучше сработал  Serial Collector и это интересно, ведь ожидалось
что распараллеливание будет работать лучше. Но это лишь показывает что нет супер-универсального сборщика и что под каждую
конеретную задачу нужно подбирать более подходящий GC.


### Out Of Memory
* 6,15 min Exception in thread "main" java.lang.OutOfMemoryError: Java heap space -Xms8G -Xmx8G
* 0,37 min Exception in thread "main" java.lang.OutOfMemoryError: Java heap space -Xms512m -Xmx512m  