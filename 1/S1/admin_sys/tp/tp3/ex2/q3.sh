

total=0

while true
do
	echo "Veuillez saisir un nombre supérieur à 0 ou q pour arreter votre saisie"
	read -r b

	if [ $b == "q" ]
		then
			break
	fi
	if [ $b -lt 1 ]
		then
			continue
		else
			total=$((total+b))
	fi	
done


result=$((total/8))
rest=$((total%8))


if [ $rest -gt 0 ]
	then
		result=$((result+1))
fi

if [ $total -le 8 ]
	then
		if [ $total -eq 1 ]
			then
				echo "Dans $total bit il ya $result octet."
			else
				echo "Dans $total bits il ya $result octet."
		fi	
	else
		echo "Dans $total bits il ya $result octets."
fi





