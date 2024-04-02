/* Operation 1 */
SELECT * 
FROM R1, R2 
WHERE C=D AND B<50

/* Operation 2 */
SELECT * 
FROM R1, R2
WHERE C=D AND B=60

/* Operation 3 */
SELECT * 
FROM R1, R2, R3
WHERE C=D AND F=G AND A=300
