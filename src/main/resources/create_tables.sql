CREATE TABLE `book` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` varchar(500) NOT NULL,
  `price` decimal(10,0) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniqueTitle` (`title`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE `bookshelf` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `category` varchar(500) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniqueCategory` (`category`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE `bookshelf_space` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `bookshelfId` INT NOT NULL,
  `bookId` INT NOT NULL,
  `quantity` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `bookshelfIdFK` FOREIGN KEY (`bookshelfId`) REFERENCES `bookshelf` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `bookIdFK` FOREIGN KEY (`bookId`) REFERENCES `book` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;