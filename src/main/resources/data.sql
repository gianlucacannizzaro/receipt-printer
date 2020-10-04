INSERT INTO TAXES (TAX_ID,APPLY_EXPRESSION,NAME,RATE) VALUES(1, 'categories.contains(item.category)','Basic sales tax',0.1);
INSERT INTO TAXES (TAX_ID,APPLY_EXPRESSION,NAME,RATE) VALUES(2, 'item.imported == true','Import duty',0.05);
INSERT INTO CATEGORIES  (CATEGORY_ID,NAME) VALUES (1,'Book');
INSERT INTO CATEGORIES  (CATEGORY_ID,NAME) VALUES (2,'Food');
INSERT INTO CATEGORIES  (CATEGORY_ID,NAME) VALUES (3,'Medical');
INSERT INTO CATEGORIES  (CATEGORY_ID,NAME) VALUES (4,'Other');
INSERT INTO TAXES_SUBJECTED_CATEGORIES(TAX_TAX_ID,SUBJECTED_CATEGORIES_CATEGORY_ID) VALUES(1,4);
INSERT INTO TAXES_SUBJECTED_CATEGORIES(TAX_TAX_ID,SUBJECTED_CATEGORIES_CATEGORY_ID) VALUES(2,1);
INSERT INTO TAXES_SUBJECTED_CATEGORIES(TAX_TAX_ID,SUBJECTED_CATEGORIES_CATEGORY_ID) VALUES(2,2);
INSERT INTO TAXES_SUBJECTED_CATEGORIES(TAX_TAX_ID,SUBJECTED_CATEGORIES_CATEGORY_ID) VALUES(2,3);
INSERT INTO TAXES_SUBJECTED_CATEGORIES(TAX_TAX_ID,SUBJECTED_CATEGORIES_CATEGORY_ID) VALUES(2,4);
INSERT INTO CATEGORY_KEYWORDS (CATEGORY_CATEGORY_ID,KEYWORDS) VALUES (1,'book');
INSERT INTO CATEGORY_KEYWORDS (CATEGORY_CATEGORY_ID,KEYWORDS) VALUES (1,'volume');
INSERT INTO CATEGORY_KEYWORDS (CATEGORY_CATEGORY_ID,KEYWORDS) VALUES (1,'paper');
INSERT INTO CATEGORY_KEYWORDS (CATEGORY_CATEGORY_ID,KEYWORDS) VALUES (2,'food');
INSERT INTO CATEGORY_KEYWORDS (CATEGORY_CATEGORY_ID,KEYWORDS) VALUES (2,'chocolate');
INSERT INTO CATEGORY_KEYWORDS (CATEGORY_CATEGORY_ID,KEYWORDS) VALUES (2,'bar');
INSERT INTO CATEGORY_KEYWORDS (CATEGORY_CATEGORY_ID,KEYWORDS) VALUES (3,'pills');
INSERT INTO CATEGORY_KEYWORDS (CATEGORY_CATEGORY_ID,KEYWORDS) VALUES (3,'medicine');
INSERT INTO CATEGORY_KEYWORDS (CATEGORY_CATEGORY_ID,KEYWORDS) VALUES (4,'perfume');
INSERT INTO CATEGORY_KEYWORDS (CATEGORY_CATEGORY_ID,KEYWORDS) VALUES (4,'cd');
